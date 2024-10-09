package eu.datex2.demo;

import java.time.Instant;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.springframework.stereotype.Service;

import eu.datex2.schema._3.common.HeaderInformation;
import eu.datex2.schema._3.common.InformationStatusEnum;
import eu.datex2.schema._3.common.InternationalIdentifier;
import eu.datex2.schema._3.common.PayloadPublication;
import eu.datex2.schema._3.common.UnderscoreInformationStatusEnum;
import eu.datex2.schema._3.exchangeinformation.Agent;
import eu.datex2.schema._3.exchangeinformation.DynamicInformation;
import eu.datex2.schema._3.exchangeinformation.ExchangeContext;
import eu.datex2.schema._3.exchangeinformation.ExchangeInformation;
import eu.datex2.schema._3.exchangeinformation.ExchangeStatusEnum;
import eu.datex2.schema._3.exchangeinformation.ProtocolTypeEnum;
import eu.datex2.schema._3.exchangeinformation.UnderscoreExchangeStatusEnum;
import eu.datex2.schema._3.exchangeinformation.UnderscoreProtocolTypeEnum;
import eu.datex2.schema._3.messagecontainer.MessageContainer;
import eu.datex2.schema._3.situation.PublicEvent;
import eu.datex2.schema._3.situation.Situation;
import eu.datex2.schema._3.situation.SituationPublication;
import eu.datex2.wsdl.snapshotpull._2020.ObjectFactory;
import jakarta.xml.bind.JAXBElement;

@Service
public class SituationService {

    private final ObjectFactory objectFactory = new ObjectFactory();

    public Situation getSituation() {
        var situation = new Situation();
        situation.setId("SIT 1");
        var headerInformation = new HeaderInformation();
        var underscoreInformationStatusEnum = new UnderscoreInformationStatusEnum();
        underscoreInformationStatusEnum.setValue(InformationStatusEnum.TECHNICAL_EXERCISE);
        headerInformation.setInformationStatus(underscoreInformationStatusEnum);
        situation.setHeaderInformation(headerInformation);
        PublicEvent publicEvent = new PublicEvent();
        publicEvent.setId("1");
        publicEvent.setVersion("1");   
        situation.getSituationRecord().add(publicEvent);
        return situation;
    }

    public PayloadPublication getPublication() {
        var situationPublication = new SituationPublication();
        situationPublication.setLang("en");
        situationPublication.setPublicationTime(getNow());
        var publicationCreator = new InternationalIdentifier();
        publicationCreator.setCountry("RO");
        publicationCreator.setNationalIdentifier("DEMO");
        situationPublication.setPublicationCreator(publicationCreator);
        situationPublication.getSituation().add(getSituation());
        return situationPublication;
    }

    public XMLGregorianCalendar getNow() {
        var cal = new GregorianCalendar();
        cal.setTimeInMillis(Instant.now().toEpochMilli());
        try {
            return DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ExchangeInformation getExchangeInformation() {
        var exchangeInformation = new ExchangeInformation();
        var exchangeContext = new ExchangeContext();
        var agent = new Agent();
        var internationalIdentifier = new InternationalIdentifier();
        internationalIdentifier.setNationalIdentifier("DEMO");
        internationalIdentifier.setCountry("RO");        
        agent.setInternationalIdentifier(internationalIdentifier);
        exchangeContext.setSupplierOrCisRequester(agent);
        var underscoreProtocolTypeEnum = new UnderscoreProtocolTypeEnum();
        underscoreProtocolTypeEnum.setValue(ProtocolTypeEnum.SNAPSHOT_PULL);
        exchangeContext.setCodedExchangeProtocol(underscoreProtocolTypeEnum);
        exchangeContext.setExchangeSpecificationVersion("2020");
        exchangeInformation.setExchangeContext(exchangeContext);
        var dynamicInformation = new DynamicInformation();
        dynamicInformation.setMessageGenerationTimestamp(getNow());
        var underscoreExchangeStatusEnum = new UnderscoreExchangeStatusEnum();
        underscoreExchangeStatusEnum.setValue(ExchangeStatusEnum.UNDEFINED);
        dynamicInformation.setExchangeStatus(underscoreExchangeStatusEnum);
        exchangeInformation.setDynamicInformation(dynamicInformation);            
        return exchangeInformation;
    }

    public JAXBElement<MessageContainer> getJAXBMessageContainer() {
        MessageContainer messageContainer = new MessageContainer();
        messageContainer.setModelBaseVersion("3");
        messageContainer.setProfileName("SituationPublication Profile");
        messageContainer.getPayload().add(getPublication());
        return objectFactory.createPullSnapshotDataOutput(messageContainer);
    }
}
