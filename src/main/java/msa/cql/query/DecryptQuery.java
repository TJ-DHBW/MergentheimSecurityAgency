package msa.cql.query;

import java.util.regex.MatchResult;

public class DecryptQuery extends BaseQuery {
    public DecryptQuery() {
        super("^decrypt message \"(.+)\" using (\\S+) and keyfile (\\S+)$");
    }

    @Override
    public String execute(MatchResult matchResult) {
        //TODO implement
        return null;
    }
}
