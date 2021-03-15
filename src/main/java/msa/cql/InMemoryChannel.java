package msa.cql;

import com.google.common.eventbus.EventBus;
import msa.db.model.Channel;
import msa.db.model.Participant;

import java.util.ArrayList;

@SuppressWarnings("UnstableApiUsage")
public class InMemoryChannel {
    private final EventBus eventBus;
    private final InMemoryParticipant participant1;
    private final InMemoryParticipant participant2;
    private final ArrayList<InMemoryIntruder> intruders;
    private QueryContext context;

    private InMemoryChannel(String channelName, Participant participant1, Participant participant2) {
        this.eventBus = new EventBus(channelName);
        this.participant1 = InMemoryParticipant.toInMemory(participant1);
        this.participant2 = InMemoryParticipant.toInMemory(participant2);
        this.eventBus.register(this.participant1);
        this.eventBus.register(this.participant2);
        this.intruders = new ArrayList<>();
    }

    public InMemoryChannel(Channel channel) {
        this(channel.getName(), channel.getParticipant1(), channel.getParticipant2());
    }

    public void intrude(InMemoryIntruder intruder) {
        intruders.add(intruder);
        intruder.setContext(context);
        eventBus.register(intruder);
    }

    public void setContext(QueryContext context) {
        this.context = context;
        this.participant1.setContext(context);
        this.participant2.setContext(context);
        for (InMemoryIntruder intruder : intruders) {
            intruder.setContext(context);
        }
    }
}
