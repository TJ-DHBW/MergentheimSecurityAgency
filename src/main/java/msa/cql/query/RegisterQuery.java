package msa.cql.query;

import java.util.regex.MatchResult;

public class RegisterQuery extends BaseQuery {
    public RegisterQuery() {
        super("^register participant (\\S+) with type (\\S+)$");
    }

    @Override
    public String execute(MatchResult matchResult) {
        //TODO implement
        return null;
    }
}
