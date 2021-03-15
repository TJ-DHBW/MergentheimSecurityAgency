package msa.cql;

import com.google.common.eventbus.Subscribe;
import msa.db.model.Participant;

public class InMemoryParticipant {
    protected Participant participant;

    protected InMemoryParticipant() {

    }

    private InMemoryParticipant(Participant participant) {
        this.participant = participant;
    }

    public static InMemoryParticipant toInMemory(Participant participant) {
        if (participant.getType().getName().equals("intruder")) {
            return new InMemoryIntruder(participant);
        }
        return new InMemoryParticipant(participant);
    }

    @Subscribe
    public void receive(MessageEvent event) {
        //TODO implement
    }
}
