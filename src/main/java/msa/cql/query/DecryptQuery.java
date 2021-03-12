package msa.cql.query;

import msa.cql.cryptography.CryptographyService;

import java.util.regex.MatchResult;

public class DecryptQuery extends BaseQuery {
    public DecryptQuery() {
        super("^decrypt message \"(.+)\" using (\\S+) and keyfile (\\S+)$");
    }

    //TODO Test this
    @Override
    public String execute(MatchResult matchResult) {
        return CryptographyService.decrypt(matchResult.group(1), matchResult.group(2), matchResult.group(3));
    }
}
