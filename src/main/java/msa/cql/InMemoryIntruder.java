package msa.cql;

import com.google.common.eventbus.Subscribe;
import msa.cql.cryptography.CryptographyService;
import msa.db.model.Participant;
import msa.db.model.Postbox;

public class InMemoryIntruder extends InMemoryParticipant {

    public InMemoryIntruder(Participant participant) {
        this.participant = participant;
    }

    //TODO Test this
    @Override
    @Subscribe
    public void receive(MessageEvent event) {
        Participant receiver = context.getDatabase().findParticipantByName(participant.getName());
        if (receiver == null)
            throw new IllegalStateException("This participant does not exist. Which is not possible.");
        Participant sender = context.getDatabase().findParticipantByName(event.getSenderName());
        if (sender == null) throw new IllegalStateException("The sender does not exist. Which should not be possible.");

        Postbox postbox = new Postbox(receiver, sender, "unknown");
        Integer identifier = (Integer) context.getDatabase().save(postbox);


        String keyFileName = event.getKeyFileName();
        if (event.getAlgorithm().equals("rsa")) keyFileName = keyFileName.replaceFirst("\\.txt$", "_public.txt");
        String crackedMessage = CryptographyService.crack(event.getEncryptedMessage(), event.getAlgorithm(), keyFileName, 30);

        if (crackedMessage != null) {
            Postbox postboxUpdated = new Postbox(receiver, sender, crackedMessage);
            postboxUpdated.setId(identifier);
            context.getDatabase().update(postboxUpdated);
            context.addInfo("intruder " + participant.getName() + " cracked message from participant " + event.getSenderName() + " | " + crackedMessage);
        } else {
            context.addInfo("intruder " + participant.getName() + " | crack message from participant " + sender.getName() + " failed");
        }
    }
}
