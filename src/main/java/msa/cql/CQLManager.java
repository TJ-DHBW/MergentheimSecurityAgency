package msa.cql;

import msa.Configuration;
import msa.cql.query.*;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CQLManager {
    private static final ArrayList<BaseQuery> queries;

    static {
        queries = new ArrayList<>();
        //TODO remove the TestQuery
        queries.add(new TestQuery());

        queries.add(new RegisterQuery());
        queries.add(new CreateChannelQuery());
        queries.add(new IntrudeChannelQuery());
        queries.add(new EncryptQuery());
        queries.add(new DecryptQuery());
        //TODO add all the queries to the List ^-^
    }

    public String handle(String queryString) {
        if (Configuration.instance.verbose) System.out.println("Handling: " + queryString);

        for (BaseQuery query : queries) {
            Matcher matcher = query.getPattern().matcher(queryString);
            if (matcher.find()) {
                //TODO Also hand over the database to manipulate.
                return query.execute(matcher.toMatchResult());
            }
        }

        return null;
    }
}
