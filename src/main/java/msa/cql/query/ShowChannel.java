package msa.cql.query;

import msa.cql.QueryContext;

import java.util.regex.MatchResult;

public class ShowChannel extends BaseQuery{
    public ShowChannel() {
        super("show channel");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        //TODO implement execute
    }
}
