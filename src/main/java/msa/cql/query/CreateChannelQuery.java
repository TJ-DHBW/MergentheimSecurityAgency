package msa.cql.query;

import msa.cql.QueryContext;

import java.util.regex.MatchResult;

public class CreateChannelQuery extends BaseQuery {
    public CreateChannelQuery() {
        super("^create channel (\\S+) from (\\S+) to (\\S+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        context.setQueryResult(" ");
        //TODO implement
    }
}
