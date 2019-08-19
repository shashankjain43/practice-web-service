package com.snapdeal.ums.core.entity;
import java.util.*;

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Transaction;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class Application {

	/**
	 * @param args
	 */
	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	
	public User save(
			User user) {
		   sessionFactory.getCurrentSession().save(user);
	        return user;
}
}