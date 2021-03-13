package msa.cql;

import com.google.common.eventbus.Subscribe;
import msa.db.model.Participant;

public class InMemoryParticipant {
    private final Participant participant;

    public InMemoryParticipant(Participant participant) {
        this.participant = participant;
    }

    @Subscribe
    public void receive(MessageEvent event) {
        //TODO implement
    }
}
