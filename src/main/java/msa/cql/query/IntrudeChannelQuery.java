package msa.cql.query;

import java.util.regex.MatchResult;

public class IntrudeChannelQuery extends BaseQuery {
    public IntrudeChannelQuery() {
        super("^intrude channel (\\S+) by (\\S+)$");
    }

    @Override
    public String execute(MatchResult matchResult) {
        //TODO implement
        return null;
    }
}
