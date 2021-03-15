package msa.cql.query;

import msa.cql.QueryContext;

import java.util.regex.MatchResult;

public class SendQuery extends BaseQuery{
    public SendQuery() {
        super("^send message \"(.+)\" from (.+)to (\\S+) using (\\S+) and keyfile (\\S+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        //TODO implement execute
    }
}
