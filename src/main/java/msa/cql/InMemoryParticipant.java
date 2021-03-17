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
        if(context == null){
            System.out.println("context is null");
        }
        if(context.getDatabase() == null){
            System.out.println("db is null");
            return;
        }
        if(this.participant == null){
            System.out.println("participant did not get from db");
        }
        if(this.participant.getName().equals(event.getReceiverName())){
            String message = CryptographyService.decrypt(event.getEncryptedMessage(), event.getAlgorithm(), event.getKeyFileName());
            if(message == null || message.equals("")){
                System.out.println("message null");
            }
            Postbox postbox = new Postbox(this.participant, context.getDatabase().findParticipantByName(event.getSenderName()), message);
            //TODO check if postbox is supposed to be like this
            context.getDatabase().save(postbox);
            context.setQueryResult(participant.getName()+" received new message");
        }
    }

    public void setContext(QueryContext context) {
        this.context = context;
    }
}
