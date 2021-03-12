package msa.cql.query;

import msa.cql.cryptography.CryptographyService;

import java.util.regex.MatchResult;

public class EncryptQuery extends BaseQuery {
    public EncryptQuery() {
        super("^encrypt message \"(.+)\" using (\\S+) and keyfile (\\S+)$");
    }

    //TODO Test this
    @Override
    public String execute(MatchResult matchResult) {
        return CryptographyService.encrypt(matchResult.group(1), matchResult.group(2), matchResult.group(3));
    }
}
