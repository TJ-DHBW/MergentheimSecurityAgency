package msa.cql.query;

import msa.cql.QueryContext;
import msa.db.model.Channel;

import java.util.regex.MatchResult;

public class DropChannelQuery extends BaseQuery {
    public DropChannelQuery() {
        super("^drop channel (\\S+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        Channel channelToDrop = context.getDatabase().findChannelByName(matchResult.group(1));
        if (channelToDrop == null) {
            context.setQueryResult("unknown channel " + channelToDrop.getName());
            return;
        }
        context.getChannelz().remove(channelToDrop);
        context.getDatabase().delete(channelToDrop);
        context.setQueryResult("channel " + channelToDrop.getName() + " deleted");
    }
}
