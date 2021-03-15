package msa.cql.query;

import msa.cql.QueryContext;

import java.util.regex.MatchResult;

public class DropChannelQuery extends BaseQuery{
    public DropChannelQuery() {
        super("^drop channel (\\S+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        //TODO implement execute
    }
}
