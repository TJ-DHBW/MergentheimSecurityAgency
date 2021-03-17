package msa.cql.query;

import msa.cql.Logger;
import msa.cql.QueryContext;
import msa.cql.cryptography.CryptographyService;

import java.util.regex.MatchResult;

public class CrackRSAQuery extends BaseQuery{

    public CrackRSAQuery() {
        super("^crack encrypted message \"(.+)\" using rsa and keyfile (.+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        String plainText = CryptographyService.crack(matchResult.group(1), "rsa", matchResult.group(2), 30);
        if (context.isDebugOn())
            Logger.logCrack(matchResult.group(1), plainText, "RSA", matchResult.group(2));
        if(plainText == null){
            context.setQueryResult("cracking encrypted message "+matchResult.group(1)+" failed");
        }
        else {
            context.setQueryResult(plainText);
        }
    }
}
