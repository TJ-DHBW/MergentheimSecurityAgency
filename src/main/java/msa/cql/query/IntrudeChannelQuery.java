package msa.cql.query;

import msa.cql.InMemoryChannel;
import msa.cql.InMemoryIntruder;
import msa.cql.InMemoryParticipant;
import msa.cql.QueryContext;
import msa.db.model.Channel;
import msa.db.model.Participant;

import java.util.regex.MatchResult;

public class IntrudeChannelQuery extends BaseQuery {
    public IntrudeChannelQuery() {
        super("^intrude channel (\\S+) by (\\S+)$");
    }

    //TODO Test this
    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        String channelName = matchResult.group(1);
        String intruderName = matchResult.group(2);

        // Get inMemoryChannel
        Channel databaseChannel = context.getDatabase().findChannelByName(channelName);
        if (databaseChannel == null) {
            context.setQueryResult("The channel " + channelName + " does not exist.");
            return;
        }
        InMemoryChannel channel = context.getChannelz().get(databaseChannel.getName());

        // Get Participant(has to be intruder)
        Participant databaseParticipant = context.getDatabase().findParticipantByName(intruderName);
        if (databaseParticipant == null) {
            context.setQueryResult("The Participant " + intruderName + " does not exist.");
            return;
        }
        if (!databaseParticipant.getType().getName().equals("intruder")) {
            context.setQueryResult("The Participant " + intruderName + " is not an intruder.");
            return;
        }
        InMemoryIntruder intruder = (InMemoryIntruder) InMemoryParticipant.toInMemory(databaseParticipant);

        channel.intrude(intruder);

        context.setQueryResult(" ");
    }
}
