package msa.cql.query;

import msa.cql.QueryContext;
import msa.db.model.Participant;
import msa.db.model.Type;

import java.util.regex.MatchResult;

public class RegisterParticipantQuery extends BaseQuery {
    public RegisterParticipantQuery() {
        super("^register participant (\\S+) with type (\\S+)$");
    }

    @Override
    public void execute(MatchResult matchResult, QueryContext context) {
        Participant participant = context.getDatabase().findParticipantByName(matchResult.group(1));
        if (participant == null) {
            Type type;
            if ((type = context.getDatabase().findTypeByName(matchResult.group(2))) != null) {
                participant = new Participant(matchResult.group(1), type);
                context.getDatabase().save(participant);

                context.setQueryResult("participant " + participant.getName() + " with type " + participant.getType().getName() + " registered and postbox_" + participant.getName() + " created");
            } else {
                context.setQueryResult("the given type " + matchResult.group(2) + " does not exist.");
            }
        } else {
            context.setQueryResult("participant " + matchResult.group(1) + " already exists, using existing postbox_" + matchResult.group(1));
        }
    }
}
