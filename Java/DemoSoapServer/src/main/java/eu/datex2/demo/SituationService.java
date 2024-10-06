package eu.datex2.demo;

import eu.datex2.schema._3.common.PayloadPublication;
import eu.datex2.schema._3.situation.Situation;
import eu.datex2.schema._3.situation.SituationPublication;

public class SituationService {
    public PayloadPublication getPublication(){
        SituationPublication publication = new SituationPublication();
        Situation situation = new Situation();
        situation.setId("Test1");
        publication.getSituation().add(situation);
        return publication;
    }
}
