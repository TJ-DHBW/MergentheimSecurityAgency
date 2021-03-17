package msa.cql.query;

import msa.cql.InMemoryChannel;
import msa.cql.MessageEvent;
import msa.cql.QueryContext;
import msa.cql.cryptography.CryptographyService;
import msa.db.model.Algorithm;
import msa.db.model.Channel;
import msa.db.model.Message;
import msa.db.model.Participant;

import java.util.regex.MatchResult;

public class SendQuery extends BaseQuery{
    public SendQuery() {
        super("^send message \"(.+)\" from (.+)to (\\S+) using (\\S+) and keyfile (\\S+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        Participant sender = context.getDatabase().findParticipantByName(matchResult.group(2));
        Participant receiver = context.getDatabase().findParticipantByName(matchResult.group(3));
        String message = matchResult.group(1);
        String algorithm = matchResult.group(4);
        String keyFile = matchResult.group(5);
        Channel channel = context.getDatabase().findChannelByParticipants(sender, receiver);
        if(channel == null){
            channel = context.getDatabase().findChannelByParticipants(receiver, sender);
        }
        if(sender == null){
            context.setQueryResult("sender does not exist");
            return;
        }
        if(receiver == null){
            context.setQueryResult("receiver does not exist");
            return;
        }
        if(channel == null){
            context.setQueryResult("no valid channel from " + sender.getName() + " to " + receiver.getName());
            return;
        }
        String encryptedMessage = CryptographyService.encrypt(message, algorithm, keyFile);
        InMemoryChannel inMemoryChannel = new InMemoryChannel(channel);
        inMemoryChannel.setContext(context);
        MessageEvent messageEvent = new MessageEvent(encryptedMessage, algorithm, keyFile, sender.getName(), receiver.getName());
        inMemoryChannel.getEventBus().post(messageEvent);
        Algorithm algorithmClass =  context.getDatabase().findAlgorithmByName(algorithm);
        if(algorithmClass == null){
            algorithmClass = new Algorithm(algorithm);
            context.getDatabase().save(algorithmClass);
        }
        context.getDatabase().save(new Message(sender, receiver, message, algorithmClass, encryptedMessage, keyFile));
    }
}
