package msa.cql.query;

import msa.cql.QueryContext;
import msa.db.model.Channel;

import java.util.List;
import java.util.regex.MatchResult;

public class ShowChannelQuery extends BaseQuery{
    public ShowChannelQuery() {
        super("show channel");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        List<Channel> channelList = context.getDatabase().getAllChannel();
        if(channelList == null || channelList.size() == 0){
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for(Channel channel : channelList){
            stringBuilder.append(channel.getName()).append("\t").append("|");
            stringBuilder.append(channel.getParticipant1().getName());
            stringBuilder.append(" and ");
            stringBuilder.append(channel.getParticipant2().getName());
            stringBuilder.append("\n");
        }
        context.setQueryResult(stringBuilder.toString());
    }
}
