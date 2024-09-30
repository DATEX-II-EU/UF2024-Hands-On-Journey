using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using DemoSoapServer.Data;
using DemoSoapServer;
namespace DemoSoapServer.Controllers
{
    public class SituationPublicationService : DemoSoapServer.snapshotPullInterface
    {
        private readonly IDataManager _dataManager;
        public SituationPublicationService(IDataManager dataManager)
        {
            _dataManager = dataManager;
        }
        public pullSnapshotDataResponse pullSnapshotData(pullSnapshotDataRequest request)
        {
           
            pullSnapshotDataResponse response = null;
            MessageContainer msg =_dataManager.GetData();
            if(msg != null)
            {
                response = new pullSnapshotDataResponse();
                msg.exchangeInformation.exchangeContext.supplierOrCisRequester.internationalIdentifier.nationalIdentifier = "DEMO";
                msg.exchangeInformation.exchangeContext.supplierOrCisRequester.internationalIdentifier.country = "SE";
                msg.exchangeInformation.dynamicInformation.messageGenerationTimestamp = DateTime.UtcNow;
                response.pullSnapshotDataOutput = msg;
                
            }
            else
            {

                #region Return fail message

                response = new pullSnapshotDataResponse();
                response.pullSnapshotDataOutput = new MessageContainer();
                response.pullSnapshotDataOutput.modelBaseVersion = "3";

                ExchangeInformation exchangeInfo = new ExchangeInformation();
                ExchangeContext exchangeContext = new ExchangeContext();

                exchangeContext.codedExchangeProtocol = new _ProtocolTypeEnum() { Value = ProtocolTypeEnum.snapshotPull };
                exchangeContext.exchangeSpecificationVersion = "3";

                Agent agent = new Agent();
                InternationalIdentifier identifier = new InternationalIdentifier { nationalIdentifier = "DEMO", country = "SE" };

                agent.internationalIdentifier = identifier;
                exchangeContext.supplierOrCisRequester = agent;
                exchangeInfo.exchangeContext = exchangeContext;

                DynamicInformation dynamicInformation = new DynamicInformation();
                _ExchangeStatusEnum exchangeStatusEnum = new _ExchangeStatusEnum { Value = ExchangeStatusEnum.undefined };
                dynamicInformation.messageGenerationTimestamp = DateTime.UtcNow;
                dynamicInformation.exchangeStatus = exchangeStatusEnum;
                exchangeInfo.dynamicInformation = dynamicInformation;

                dynamicInformation.returnInformation = new ReturnInformation();
                dynamicInformation.returnInformation.returnStatus = new _ExchangeReturnEnum();
                dynamicInformation.returnInformation.returnStatus.Value = ExchangeReturnEnum.fail;
                #endregion

            }
            return response;
        }

        public Task<pullSnapshotDataResponse> pullSnapshotDataAsync(pullSnapshotDataRequest request)
        {
            throw new NotImplementedException();
        }
    }
}
