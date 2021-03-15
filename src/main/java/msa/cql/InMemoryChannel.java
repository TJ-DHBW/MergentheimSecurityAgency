package msa.cql;

import com.google.common.eventbus.EventBus;
import msa.db.IMSADatabase;
import msa.db.model.Channel;
import msa.db.model.Participant;

@SuppressWarnings("UnstableApiUsage")
public class InMemoryChannel {
    private final EventBus eventBus;
    private final InMemoryParticipant participant1;
    private final InMemoryParticipant participant2;
    private IMSADatabase database;

    private InMemoryChannel(String channelName, Participant participant1, Participant participant2) {
        this.eventBus = new EventBus(channelName);
        this.participant1 = InMemoryParticipant.toInMemory(participant1);
        this.participant2 = InMemoryParticipant.toInMemory(participant2);
        this.eventBus.register(this.participant1);
        this.eventBus.register(this.participant2);
    }

    public InMemoryChannel(Channel channel) {
        this(channel.getName(), channel.getParticipant1(), channel.getParticipant2());
    }

    public void intrude(InMemoryIntruder intruder) {
        eventBus.register(intruder);
    }

    public void setDatabase(IMSADatabase database) {
        this.database = database;
        this.participant1.setDatabase(database);
        this.participant2.setDatabase(database);
    }
}
