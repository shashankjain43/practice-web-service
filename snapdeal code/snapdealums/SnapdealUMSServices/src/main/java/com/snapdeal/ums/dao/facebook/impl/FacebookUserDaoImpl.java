package com.snapdeal.ums.dao.facebook.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.facebook.FacebookUser;
import com.snapdeal.ums.dao.facebook.IFacebookUserDao;
/**
 * 
 * @author fanendra
 *
 */
@Repository("fbUserDao")
public class FacebookUserDaoImpl implements IFacebookUserDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public boolean addIfNotExistsFacebookUser(FacebookUser fbUser) {
		FacebookUser facebookUser = getFacebookUser(fbUser.getEmailId());
		if(facebookUser != null){
			return false;
		}
		sessionFactory.getCurrentSession().save(fbUser);
		return true;
	}

	@Override
	public void updateFacebookUser(FacebookUser fbUser) {
		sessionFactory.getCurrentSession().update(fbUser);
	}

	@Override
	public FacebookUser getFacebookUser(User user) {
		Query query = sessionFactory.getCurrentSession().createQuery("select fb from FacebookUser fb where fb.user=:user");
		query.setParameter("user", user);
		return (FacebookUser)query.uniqueResult();
	}

	@Override
	public FacebookUser getFacebookUserbySDId(Long userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("select fb from FacebookUser fb where fb.user.id=:userId");
		query.setParameter("userId", userId);
		return (FacebookUser)query.uniqueResult();
	}

	@Override
	public FacebookUser getFacebookUserbyFBId(Long userId) {
		Query query = sessionFactory.getCurrentSession().createQuery("select fb from FacebookUser fb where fb.facebookId=:fbId");
		query.setParameter("fbId", userId);
		return (FacebookUser)query.uniqueResult();
	}

	@Override
	public FacebookUser getFacebookUser(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery("select fb from FacebookUser fb where fb.emailId=:email");
		query.setParameter("email", email);
		return (FacebookUser)query.uniqueResult();
	}
	
}
