package msa.cql.query;

import msa.cql.QueryContext;

import java.util.regex.MatchResult;

public class IntrudeChannelQuery extends BaseQuery {
    public IntrudeChannelQuery() {
        super("^intrude channel (\\S+) by (\\S+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        context.setQueryResult(" ");
        //TODO implement
    }
}
