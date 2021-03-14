package msa.cql.query;

import msa.cql.Logger;
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
        String plainText = CryptographyService.decrypt(matchResult.group(1), matchResult.group(2), matchResult.group(3));
        if (context.isDebugOn())
            Logger.logDecryption(matchResult.group(1), plainText, matchResult.group(2), matchResult.group(3));
        context.setQueryResult(plainText);
    }
}
