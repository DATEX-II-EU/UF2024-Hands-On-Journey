using DemoSoapServer;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Net.Http;
using System.Threading.Tasks;
using System.Xml.Serialization;

namespace DemoSoapServer.Data
{
    public class DataManager : IDataManager
    {
    
    public MessageContainer GetData()
    {
            MessageContainer messageContainer = null;
            messageContainer = new MessageContainer();
            messageContainer.profileName = "SituationPublication Profile";
            ExchangeInformation exchangeInformation = new ExchangeInformation();
            messageContainer.exchangeInformation = exchangeInformation;
            exchangeInformation.exchangeContext = new ExchangeContext();
            exchangeInformation.exchangeContext.supplierOrCisRequester = new Agent();
            exchangeInformation.dynamicInformation = new DynamicInformation();
            exchangeInformation.exchangeContext.supplierOrCisRequester.internationalIdentifier = new InternationalIdentifier();
            exchangeInformation.exchangeContext.supplierOrCisRequester.internationalIdentifier.nationalIdentifier = "DEMO";
            exchangeInformation.exchangeContext.supplierOrCisRequester.internationalIdentifier.country = "RO";
            exchangeInformation.exchangeContext.codedExchangeProtocol = new _ProtocolTypeEnum();
            exchangeInformation.exchangeContext.codedExchangeProtocol.Value = ProtocolTypeEnum.snapshotPull;
            exchangeInformation.exchangeContext.exchangeSpecificationVersion = "2020";
            
            exchangeInformation.dynamicInformation.messageGenerationTimestamp = DateTime.UtcNow;             
            exchangeInformation.dynamicInformation.exchangeStatus = new _ExchangeStatusEnum();
            exchangeInformation.dynamicInformation.exchangeStatus.Value = ExchangeStatusEnum.undefined;
            
            messageContainer.payload = new SituationPublication[1];
            SituationPublication situationPublication = new SituationPublication();
            messageContainer.payload[0] = situationPublication;

            situationPublication.lang = "en";
            situationPublication.publicationTime = DateTime.Now;
            situationPublication.publicationCreator = new InternationalIdentifier();
            situationPublication.publicationCreator.country = "RO";
            situationPublication.publicationCreator.nationalIdentifier = "DEMO";

            List<Situation> situations = new List<Situation>();
            Situation situation = new Situation();
            situation.id = "SIT 1";
            situation.headerInformation = new HeaderInformation();
            situation.headerInformation.informationStatus = new _InformationStatusEnum();
            situation.headerInformation.informationStatus.Value = InformationStatusEnum.technicalExercise;
            
            List<SituationRecord> situationRecords = new List<SituationRecord>();

            PublicEvent publicEvent = new PublicEvent();
            publicEvent.id = "1";
            publicEvent.version = "1";

            
            situationRecords.Add(publicEvent);
            situation.situationRecord = situationRecords.ToArray();
            
            
            situations.Add(situation);

            situationPublication.situation = situations.ToArray();

            return messageContainer;

    }

    }
}
