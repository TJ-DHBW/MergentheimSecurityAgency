package msa.cql.query;

import msa.cql.Logger;
import msa.cql.QueryContext;
import msa.cql.cryptography.CryptographyService;

import java.util.regex.MatchResult;

public class CrackShiftQuery extends BaseQuery{
    public CrackShiftQuery() {
        super("^crack encrypted message \"(.+)\" using shift$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        String plainText = CryptographyService.crack(matchResult.group(1), "shift", null, 30);
        if (context.isDebugOn())
            Logger.logCrack(matchResult.group(1), plainText, "SHIFT", null);
        if(plainText == null){
            context.setQueryResult("");
        }
        else {
            context.setQueryResult(plainText);
        }
    }
}
