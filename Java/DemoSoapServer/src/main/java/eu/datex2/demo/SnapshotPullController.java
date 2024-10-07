package eu.datex2.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import eu.datex2.schema._3.messagecontainer.MessageContainer;
import jakarta.xml.bind.JAXBElement;

@RestController
public class SnapshotPullController {

    private SituationService situationService;

    public SnapshotPullController(SituationService situationService){
        this.situationService = situationService;
    }

    @GetMapping("/")
    public JAXBElement<MessageContainer> getRoot() {
        return situationService.getJAXBMessageContainer();
    }
}
