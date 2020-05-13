package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Participant;

@Component("participantService")
public class ParticipantService
{

    DatabaseConnector connector;

    public ParticipantService()
    {
        connector = DatabaseConnector.getInstance();
    }

    public Collection<Participant> getAll()
    {
        return connector.getSession().createCriteria(Participant.class).list();
    }

    public Participant findByLogin(String login)
    {
        String hql = "FROM Participant WHERE login = '" + login + "'";
        return (Participant) connector.getSession().createQuery(hql).uniqueResult();
    }

    public Participant add(Participant participant)
    {
        Transaction transaction = connector.getSession().beginTransaction();
        connector.getSession().save(participant);
        transaction.commit();
        return participant;
    }

}
