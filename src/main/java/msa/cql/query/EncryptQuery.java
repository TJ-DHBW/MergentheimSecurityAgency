package msa.cql.query;

import msa.cql.Logger;
import msa.cql.QueryContext;
import msa.cql.cryptography.CryptographyService;

import java.util.regex.MatchResult;

public class EncryptQuery extends BaseQuery {
    public EncryptQuery() {
        super("^encrypt message \"(.+)\" using (\\S+) and keyfile (\\S+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        String cypher = CryptographyService.encrypt(matchResult.group(1), matchResult.group(2), matchResult.group(3));
        if (context.isDebugOn())
            Logger.logEncryption(matchResult.group(1), cypher, matchResult.group(2), matchResult.group(3));
        context.setQueryResult(cypher);
    }
}
