package msa.cql;

import msa.db.IMSADatabase;

public class QueryContext {
    private final IMSADatabase database;

    private String queryResult;

    public QueryContext(IMSADatabase database) {
        this.database = database;
    }

    public IMSADatabase getDatabase() {
        return database;
    }

    public String pullQueryResult() {
        if (!queryResult.equals("")) {
            String tmp = queryResult;
            queryResult = "";
            return tmp;
        } else {
            //TODO Maybe this has to go. >.< We will see...
            throw new IllegalStateException("The queryResult has not been set yet!");
        }
    }

    public void setQueryResult(String queryResult) {
        if (queryResult.equals("")) {
            this.queryResult = queryResult;
        } else {
            throw new IllegalStateException("The last queryResult has not yet been read!");
        }
    }
}
