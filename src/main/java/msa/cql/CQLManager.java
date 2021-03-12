package msa.cql;

import msa.Configuration;
import msa.cql.query.BaseQuery;
import msa.cql.query.TestQuery;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CQLManager {
    private static final ArrayList<BaseQuery> queries;

    static {
        queries = new ArrayList<>();
        //TODO remove the TestQuery
        queries.add(new TestQuery());
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
