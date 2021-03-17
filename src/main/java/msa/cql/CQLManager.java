package msa.cql;

import msa.Configuration;
import msa.cql.query.*;
import msa.db.IMSADatabase;

import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CQLManager {
    private static final ArrayList<BaseQuery> queries;
    private final QueryContext context;

    static {
        queries = new ArrayList<>();

        queries.add(new RegisterParticipantQuery());
        queries.add(new CreateChannelQuery());
        queries.add(new IntrudeChannelQuery());
        queries.add(new EncryptQuery());
        queries.add(new DecryptQuery());
        queries.add(new DropChannelQuery());
        queries.add(new SendQuery());
        queries.add(new ShowChannelQuery());
        queries.add(new CrackShiftQuery());
        queries.add(new CrackRSAQuery());
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


    public void displayMostCurrentLogFile() {
        File directory = new File(Configuration.instance.logFileFolder);
        File[] files = directory.listFiles(File::isFile);
        long lastModifiedTime = Long.MIN_VALUE;
        File chosenFile = null;
        if (files != null) {
            for (File file : files) {
                Pattern pattern = Pattern.compile("^(encrypt|decrypt|crack)_([^_]*)_([^_]*).txt$");
                Matcher m = pattern.matcher(file.getName());
                m.matches();
                Long lastModified = Long.parseLong(m.group(3));
                if (lastModified > lastModifiedTime) {
                    chosenFile = file;
                    lastModifiedTime = lastModified;
                }
            }
        }
        else{
            this.getContext().setQueryResult("no logfiles found");
            return;
        }
        if (chosenFile == null){
            this.getContext().setQueryResult("no logfiles found");
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(chosenFile)));
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
            this.getContext().setQueryResult(stringBuilder.toString());
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
