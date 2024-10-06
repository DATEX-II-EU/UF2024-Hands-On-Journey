package eu.datex2.demo;

import static eu.datex2.demo.Constants.NAMESPACE;
import static eu.datex2.demo.Constants.MODEL_BASE_VERSION;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import eu.datex2.schema._3.messagecontainer.MessageContainer;
import eu.datex2.wsdl.snapshotpull._2020.ObjectFactory;
import jakarta.xml.bind.JAXBElement;

@Endpoint
public class SnapshotPullEndpoint {

    private final ObjectFactory objectFactory = new ObjectFactory();
    private SituationService situationService = new SituationService();

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE, localPart = "pullSnapshotData")
    public JAXBElement<MessageContainer> handlePullData() {
        MessageContainer response = new MessageContainer();
        response.setModelBaseVersion(MODEL_BASE_VERSION);
        response.getPayload().add(situationService.getPublication());
        return objectFactory.createPullSnapshotDataOutput(response);
    }
}