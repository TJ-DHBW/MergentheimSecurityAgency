package msa.cql;

import com.google.common.eventbus.Subscribe;
import msa.cql.cryptography.CryptographyService;
import msa.db.model.Participant;
import msa.db.model.Postbox;

public class InMemoryParticipant {
    protected Participant participant;
    protected QueryContext context;

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
        Participant receiver = context.getDatabase().findParticipantByName(event.getReceiverName());
        if(this.participant == context.getDatabase().findParticipantByName(event.getReceiverName())){
            String message = CryptographyService.decrypt(event.getEncryptedMessage(), event.getAlgorithm(), event.getKeyFileName());
            Postbox postbox = new Postbox(receiver, context.getDatabase().findParticipantByName(event.getSenderName()), message);
            //TODO check if postbox is supposed to be like this
            context.getDatabase().save(postbox);
            context.setQueryResult(participant.getName()+" received new message");
        }
    }

    public void setContext(QueryContext context) {
        this.context = context;
    }
}
