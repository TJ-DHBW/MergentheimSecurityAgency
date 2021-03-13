package msa.cql;

import com.google.common.eventbus.EventBus;
import msa.db.model.Channel;
import msa.db.model.Participant;

@SuppressWarnings("UnstableApiUsage")
public class InMemoryChannel {
    private final EventBus eventBus;

    private InMemoryChannel(String channelName, Participant participant1, Participant participant2) {
        eventBus = new EventBus(channelName);
        eventBus.register(new InMemoryParticipant(participant1));
        eventBus.register(new InMemoryParticipant(participant2));
    }

    public InMemoryChannel(Channel channel) {
        this(channel.getName(), channel.getParticipant1(), channel.getParticipant2());
    }
}
