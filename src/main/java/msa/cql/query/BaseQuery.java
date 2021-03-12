package msa.cql.query;

import msa.cql.QueryContext;

import java.util.regex.MatchResult;
import java.util.regex.Pattern;

public abstract class BaseQuery {
    private final Pattern pattern;

    protected BaseQuery(String regexString) {
        this.pattern = Pattern.compile(regexString, Pattern.CASE_INSENSITIVE);
    }

    public abstract void execute(MatchResult matchResult, QueryContext context);

    public Pattern getPattern() {
        return pattern;
    }
}
