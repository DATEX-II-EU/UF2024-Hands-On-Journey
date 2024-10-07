package eu.datex2.demo;

import static eu.datex2.demo.Constants.NAMESPACE;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import eu.datex2.schema._3.messagecontainer.MessageContainer;
import jakarta.xml.bind.JAXBElement;

@Endpoint
public class SnapshotPullEndpoint {

    private SituationService situationService;

    public SnapshotPullEndpoint(SituationService situationService) {
        this.situationService = situationService;
    }

    @ResponsePayload
    @PayloadRoot(namespace = NAMESPACE, localPart = "pullSnapshotData")
    public JAXBElement<MessageContainer> handlePullData() {
        return situationService.getJAXBMessageContainer();
    }
}
