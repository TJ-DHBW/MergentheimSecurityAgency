package msa.cql.query;

import java.util.regex.MatchResult;

/**
 * TestQuery that implements an echo query. Everything after "echo " is returned.
 */
public class TestQuery extends BaseQuery {
    public TestQuery() {
        super("^echo (.+)$");
    }

    @Override
    public String execute(MatchResult matchResult) {
        return matchResult.group(1);
    }
}
