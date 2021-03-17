package msa.cql;

import msa.Configuration;
import msa.db.IMSADatabase;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class QueryContext {
    private final IMSADatabase database;
    private final HashMap<String, InMemoryChannel> channelz;

    private boolean debugOn;
    private String queryResult;
    private final ArrayList<String> additionalInfo;

    public QueryContext(IMSADatabase database) {
        this.database = database;
        this.channelz = new HashMap<>();

        this.debugOn = false;
        this.queryResult = "";
        this.additionalInfo = new ArrayList<>();
    }

    public IMSADatabase getDatabase() {
        return database;
    }

    public void addChannel(String channelName, InMemoryChannel channel) {
        channel.setContext(this);
        channelz.put(channelName, channel);
    }

    public InMemoryChannel getChannel(String channelName) {
        return channelz.get(channelName);
    }

    public String pullQueryResult() {
        if (!queryResult.equals("")) {
            StringBuilder ret = new StringBuilder(queryResult);
            queryResult = "";
            if (additionalInfo.size() > 0) {
                ret.append("\n\nAdditional Info:\n");
                for (String info : additionalInfo) {
                    ret.append(info).append("\n");
                }
                additionalInfo.clear();
            }
            return ret.toString();
        } else {
            //TODO Maybe this has to go. >.< We will see...
            //TODO it has to, if there should not be an error
            //throw new IllegalStateException("The queryResult has not been set yet!");
            return "";
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

    public void addInfo(String info) {
        this.additionalInfo.add(info);
    }

    public HashMap<String, InMemoryChannel> getChannelz() {
        return channelz;
    }

}
