package msa.cql.query;

import msa.cql.Logger;
import msa.cql.QueryContext;
import msa.cql.cryptography.CryptographyService;

import java.util.regex.MatchResult;

public class CrackShiftQuery extends BaseQuery{
    public CrackShiftQuery() {
        super("^crack encrypted message \"(.+)\" using shift$");
    }

    //TODO test if works
    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        String plainText = CryptographyService.crack(matchResult.group(1), "shift", matchResult.group(2), 30);
        if (context.isDebugOn())
            Logger.logCrack(matchResult.group(1), plainText, "SHIFT", matchResult.group(2));
        context.setQueryResult(plainText);
    }
}
