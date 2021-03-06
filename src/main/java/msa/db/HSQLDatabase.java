package msa.db;

import msa.db.model.*;
import org.hibernate.Session;
import org.hibernate.Transaction;
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
        Transaction transaction = session.beginTransaction();
        Serializable returnOfSave = session.save(object);
        transaction.commit();
        return returnOfSave;
    }

    @Override
    public void delete(Object object) {
        Transaction transaction = session.beginTransaction();
        session.delete(object);
        transaction.commit();
        return;
    }

    @Override
    public void updateMessage(Integer id, String message) {
        Transaction transaction = session.beginTransaction();
        String queryString = "FROM Postbox P WHERE P.id = :id";
        Query<Postbox> query = session.createQuery(queryString, Postbox.class);
        query.setParameter("id", id);
        Postbox postbox;
        try {
            postbox = query.getSingleResult();
        } catch (NoResultException e) {
            System.out.println("More than one postbox with given ID " + id + " found.");
            return;
        }
        postbox.setMessage(message);

        transaction.commit();
    }

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

    @Override
    public List<Channel> getAllChannel() {
        return session.createQuery("FROM Channel", Channel.class).list();
    }
}
