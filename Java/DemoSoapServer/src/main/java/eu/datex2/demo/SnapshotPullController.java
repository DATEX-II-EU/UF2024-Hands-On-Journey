package eu.datex2.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import eu.datex2.schema._3.messagecontainer.MessageContainer;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;

import java.io.StringWriter;

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

    @GetMapping(value = "/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public String getJSON() throws JsonProcessingException {
        var objectMapper = new ObjectMapper();
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(situationService.getJAXBMessageContainer());
    }

    @GetMapping(value = "/xml", produces = MediaType.APPLICATION_XML_VALUE)
    public String getXML() throws JAXBException {
        var marshaller = JAXBContext.newInstance(MessageContainer.class).createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        var response = new StringWriter();
        marshaller.marshal(situationService.getJAXBMessageContainer(), response);
        return response.toString();
    }
}
