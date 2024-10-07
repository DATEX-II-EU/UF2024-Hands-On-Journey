package eu.datex2.demo;

import static eu.datex2.demo.Constants.MODEL_BASE_VERSION;

import org.springframework.stereotype.Service;
import eu.datex2.schema._3.common.PayloadPublication;
import eu.datex2.schema._3.messagecontainer.MessageContainer;
import eu.datex2.schema._3.situation.Situation;
import eu.datex2.schema._3.situation.SituationPublication;
import eu.datex2.wsdl.snapshotpull._2020.ObjectFactory;
import jakarta.xml.bind.JAXBElement;

@Service
public class SituationService {

    private final ObjectFactory objectFactory = new ObjectFactory();

    public Situation getSituation() {
        Situation situation = new Situation();
        situation.setId("Test1");
    }

    public PayloadPublication getPublication() {
        SituationPublication publication = new SituationPublication();
        publication.getSituation().add(getSituation());
        return publication;
    }

    public JAXBElement<MessageContainer> getJAXBMessageContainer() {
        MessageContainer response = new MessageContainer();
        response.setModelBaseVersion(MODEL_BASE_VERSION);
        response.getPayload().add(getPublication());
        return objectFactory.createPullSnapshotDataOutput(response);
    }
}
