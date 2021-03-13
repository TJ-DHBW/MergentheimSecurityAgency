package msa.cql;

import msa.Configuration;
import msa.cql.query.*;
import msa.db.IMSADatabase;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class CQLManager {
    private static final ArrayList<BaseQuery> queries;
    private final QueryContext context;

    static {
        queries = new ArrayList<>();
        //TODO remove the TestQuery
        queries.add(new TestQuery());

        queries.add(new RegisterParticipantQuery());
        queries.add(new CreateChannelQuery());
        queries.add(new IntrudeChannelQuery());
        queries.add(new EncryptQuery());
        queries.add(new DecryptQuery());
        //TODO add all the queries to the List ^-^
    }

    public CQLManager(IMSADatabase database) {
        this.context = new QueryContext(database);
    }

    public void handle(String queryString) {
        if (Configuration.instance.verbose) System.out.println("Handling: " + queryString);

        for (BaseQuery query : queries) {
            Matcher matcher = query.getPattern().matcher(queryString);
            if (matcher.find()) {
                query.execute(matcher.toMatchResult(), context);
                return;
            }
        }
    }

    public void handleAll(String[] queries) {
        for (String query : queries) {
            handle(query);
            context.pullQueryResult();
        }
    }

    public QueryContext getContext() {
        return context;
    }
}
