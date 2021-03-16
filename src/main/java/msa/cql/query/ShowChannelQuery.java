package msa.cql.query;

import msa.cql.QueryContext;
import msa.db.model.Channel;

import java.util.List;
import java.util.regex.MatchResult;

public class ShowChannelQuery extends BaseQuery{
    public ShowChannelQuery() {
        super("show channel");
    }

    //TODO check if works
    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        List<Channel> channelList = context.getDatabase().getAllChannel();
        StringBuilder stringBuilder = new StringBuilder();
        for(Channel channel : channelList){
            stringBuilder.append(channel.getName()).append("\t").append("|");
            stringBuilder.append(channel.getParticipant1().getName());
            stringBuilder.append(" and ");
            stringBuilder.append(channel.getParticipant2().getName());
        }
        context.setQueryResult(stringBuilder.toString());
    }
}
