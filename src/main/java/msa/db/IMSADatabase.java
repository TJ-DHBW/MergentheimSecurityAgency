package msa.db;

import msa.db.model.Algorithm;
import msa.db.model.Channel;
import msa.db.model.Participant;
import msa.db.model.Type;

import java.util.List;

public interface IMSADatabase {
    //TODO write some boring stuff in here.

    void save(Object object);

    void delete(Object object);

    Algorithm findAlgorithmByName(String name);

    Type findTypeByName(String name);

    Participant findParticipantByName(String name);

    List<Channel> findAllChannelWithParticipant(Participant participant);

    Channel findChannelByParticipants(Participant participant1, Participant participant2);

    Channel findChannelByName(String name);

    List<Channel> getAllChannel();
}
