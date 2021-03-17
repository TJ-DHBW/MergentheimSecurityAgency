package msa.db;

import msa.db.model.Algorithm;
import msa.db.model.Channel;
import msa.db.model.Participant;
import msa.db.model.Type;

import java.io.Serializable;
import java.util.List;

public interface IMSADatabase {

    Serializable save(Object object);

    void delete(Object object);

    void updateMessage(Integer id, String message);

    Algorithm findAlgorithmByName(String name);

    Type findTypeByName(String name);

    Participant findParticipantByName(String name);

    Channel findChannelByParticipants(Participant participant1, Participant participant2);

    Channel findChannelByName(String name);

    List<Channel> getAllChannel();
}
