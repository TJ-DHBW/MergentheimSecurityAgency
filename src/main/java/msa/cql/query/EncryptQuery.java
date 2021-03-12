package msa.cql.query;

import java.util.regex.MatchResult;

public class EncryptQuery extends BaseQuery {
    public EncryptQuery() {
        super("^encrypt message \"(.+)\" using (\\S+) and keyfile (\\S+)$");
    }

    @Override
    public String execute(MatchResult matchResult) {
        //TODO implement
        return null;
    }
}
