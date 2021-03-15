package msa.db;

import msa.db.model.Algorithm;
import msa.db.model.Channel;
import msa.db.model.Participant;
import msa.db.model.Type;
import org.hibernate.Session;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.io.Serializable;
import java.util.List;

public class HSQLDatabase implements IMSADatabase {
    private final Session session;

    public HSQLDatabase() {
        this.session = HibernateUtil.getSessionFactory().openSession();
    }

    public void init() {
        session.save(new Algorithm("rsa"));
        session.save(new Algorithm("shift"));
        session.save(new Type("normal"));
        session.save(new Type("intruder"));
    }

    @Override
    public Serializable save(Object object) {
        return session.save(object);
    }

    @Override
    public void delete(Object object) {
        session.delete(object);
    }

    @Override
    public void update(Object object) {
        session.update(object);
    }

    //TODO Test
    @Override
    public Algorithm findAlgorithmByName(String name) {
        String queryString = "FROM Algorithm A WHERE A.name = :name";
        Query<Algorithm> query = session.createQuery(queryString, Algorithm.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //TODO Test
    @Override
    public Type findTypeByName(String name) {
        String queryString = "FROM Type T WHERE T.name = :name";
        Query<Type> query = session.createQuery(queryString, Type.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //TODO Test
    @Override
    public Participant findParticipantByName(String name) {
        String queryString = "FROM Participant P WHERE P.name = :name";
        Query<Participant> query = session.createQuery(queryString, Participant.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //TODO IMPORTANT Test
    @Override
    public List<Channel> findAllChannelWithParticipant(Participant participant) {
        String queryString = "FROM Channel C WHERE C.participant1 = :participant OR C.participant2 = :participant";
        Query<Channel> query = session.createQuery(queryString, Channel.class);
        query.setParameter("participant", participant);
        return query.list();
    }

    //TODO IMPORTANT Test
    @Override
    public Channel findChannelByParticipants(Participant participant1, Participant participant2) {
        String queryString = "FROM Channel C WHERE (C.participant1 = :participant1 AND C.participant2 = :participant2) OR (C.participant1 = :participant2 AND C.participant2 = :participant1)";
        Query<Channel> query = session.createQuery(queryString, Channel.class);
        query.setParameter("participant1", participant1);
        query.setParameter("participant2", participant2);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //TODO Test
    @Override
    public Channel findChannelByName(String name) {
        String queryString = "FROM Channel C WHERE C.name = :name";
        Query<Channel> query = session.createQuery(queryString, Channel.class);
        query.setParameter("name", name);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    //TODO Test
    @Override
    public List<Channel> getAllChannel() {
        return session.createQuery("FROM Channel", Channel.class).list();
    }
}
