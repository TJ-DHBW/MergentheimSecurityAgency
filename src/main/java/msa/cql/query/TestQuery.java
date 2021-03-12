package msa.cql.query;

import msa.cql.QueryContext;

import java.util.regex.MatchResult;

/**
 * TestQuery that implements an echo query. Everything after "echo " is returned.
 */
public class TestQuery extends BaseQuery {
    public TestQuery() {
        super("^echo (.+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        context.setQueryResult(matchResult.group(1));
    }
}
