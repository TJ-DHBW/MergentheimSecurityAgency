package msa.cql.query;

import msa.cql.InMemoryChannel;
import msa.cql.QueryContext;
import msa.db.model.Channel;
import msa.db.model.Participant;

import java.util.regex.MatchResult;

public class CreateChannelQuery extends BaseQuery {
    public CreateChannelQuery() {
        super("^create channel (\\S+) from (\\S+) to (\\S+)$");
    }

    //TODO Simulation
    //TODO Test
    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        String channelName = matchResult.group(1);
        String name1 = matchResult.group(2);
        String name2 = matchResult.group(3);

        // Catch identical participants
        if (name1.equals(name2)) {
            context.setQueryResult(name1 + " and " + name2 + " are identical – cannot create channel on itself");
            return;
        }

        // Catch existing channel
        Channel channel = context.getDatabase().findChannelByName(channelName);
        if (channel != null) {
            context.setQueryResult("channel " + channelName + " already exists");
            return;
        }

        // Get Participants
        Participant participant1 = context.getDatabase().findParticipantByName(name1);
        if (participant1 == null) {
            context.setQueryResult("Participant " + name1 + " does not exist yet.");
            return;
        }
        Participant participant2 = context.getDatabase().findParticipantByName(name2);
        if (participant2 == null) {
            context.setQueryResult("Participant " + name2 + " does not exist yet.");
            return;
        }

        // Catch existing communication
        channel = context.getDatabase().findChannelByParticipants(participant1, participant2);
        if (channel != null) {
            context.setQueryResult("communication channel between " + name1 + " and " + name2 + " already exists");
            return;
        }

        // Create the new Channel
        channel = new Channel(channelName, participant1, participant2);
        context.getDatabase().save(channel);
        context.getChannelz().put(channel.getName(), new InMemoryChannel(channel));
        context.setQueryResult("channel " + channelName + " from " + name1 + " to " + name2 + " successfully created");
    }
}
