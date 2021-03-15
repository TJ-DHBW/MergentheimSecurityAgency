package msa.cql;

import msa.Configuration;
import msa.db.IMSADatabase;

import java.util.HashMap;

public class QueryContext {
    private final IMSADatabase database;
    private final HashMap<String, InMemoryChannel> channelz;

    private boolean debugOn;
    private String queryResult;

    public QueryContext(IMSADatabase database) {
        this.database = database;
        this.channelz = new HashMap<>();

        debugOn = false;
        queryResult = "";
    }

    public IMSADatabase getDatabase() {
        return database;
    }

    public void addChannel(String channelName, InMemoryChannel channel) {
        channel.setDatabase(database);
        channelz.put(channelName, channel);
    }

    public InMemoryChannel getChannel(String channelName) {
        return channelz.get(channelName);
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
        if (this.queryResult.equals("")) {
            this.queryResult = queryResult;
        } else {
            throw new IllegalStateException("The last queryResult has not yet been read!");
        }
    }

    public boolean isDebugOn() {
        return debugOn;
    }

    public void toggleDebug() {
        this.debugOn = !debugOn;
        if (Configuration.instance.verbose) System.out.println("Debug mode is now: " + debugOn);
    }
}
