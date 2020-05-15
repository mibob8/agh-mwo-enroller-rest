package com.company.enroller.persistence;

import java.util.Collection;

import com.company.enroller.model.Participant;
import org.hibernate.Query;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;

@Component("meetingService")
public class MeetingService {

	DatabaseConnector connector;
	Transaction transaction;

	public MeetingService() {
		connector = DatabaseConnector.getInstance();
		transaction = connector.getSession().beginTransaction();
	}

	public Collection<Meeting> getAll() {
		String hql = "FROM Meeting";
		Query query = connector.getSession().createQuery(hql);
		return query.list();
	}

	public Meeting findById(long id){
		String hql = "FROM Meeting WHERE id = " + id;
		return (Meeting) connector.getSession().createQuery(hql).uniqueResult();
	}

	public Meeting add(Meeting meeting){
		connector.getSession().save(meeting);
		transaction.commit();
		return meeting;
	}

	public void remove(Meeting meeting){
		connector.getSession().delete(meeting);
		transaction.commit();
	}

	public void update(Meeting meeting){
		connector.getSession().update(meeting);
		transaction.commit();
	}
}
