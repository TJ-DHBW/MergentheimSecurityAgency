package msa.cql;

import com.google.common.eventbus.Subscribe;
import msa.db.model.Participant;

public class InMemoryIntruder extends InMemoryParticipant {

    public InMemoryIntruder(Participant participant) {
        this.participant = participant;
    }

    @Override
    @Subscribe
    public void receive(MessageEvent event) {
        //TODO intruder stuff
    }
}
