package msa.cql.query;

import msa.cql.QueryContext;
import msa.cql.cryptography.CryptographyService;

import java.util.regex.MatchResult;

public class DecryptQuery extends BaseQuery {
    public DecryptQuery() {
        super("^decrypt message \"(.+)\" using (\\S+) and keyfile (\\S+)$");
    }

    //TODO Test this for rsa
    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        context.setQueryResult(CryptographyService.decrypt(matchResult.group(1), matchResult.group(2), matchResult.group(3)));
    }
}
