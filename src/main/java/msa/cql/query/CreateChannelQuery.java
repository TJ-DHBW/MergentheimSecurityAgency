package msa.cql.query;

import java.util.regex.MatchResult;

public class CreateChannelQuery extends BaseQuery {
    public CreateChannelQuery() {
        super("^create channel (\\S+) from (\\S+) to (\\S+)$");
    }

    @Override
    public String execute(MatchResult matchResult) {
        //TODO implement
        return null;
    }
}
