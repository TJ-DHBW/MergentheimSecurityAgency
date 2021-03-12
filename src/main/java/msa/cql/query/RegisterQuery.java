package msa.cql.query;

import msa.cql.QueryContext;

import java.util.regex.MatchResult;

public class RegisterQuery extends BaseQuery {
    public RegisterQuery() {
        super("^register participant (\\S+) with type (\\S+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        context.setQueryResult(" ");
        //TODO implement
    }
}
