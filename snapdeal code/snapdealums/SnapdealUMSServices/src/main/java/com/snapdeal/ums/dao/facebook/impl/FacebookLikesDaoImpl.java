/**
 * 
 */
package com.snapdeal.ums.dao.facebook.impl;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.facebook.FacebookLike;
import com.snapdeal.ums.dao.facebook.IFacebookLikesDao;

/**
 * @author fanendra
 *
 */
@Repository("fbLikeDao")
public class FacebookLikesDaoImpl implements IFacebookLikesDao{
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Override
	public Set<FacebookLike> getUserFacebookLikesBySDId(Long id) {
		Query query = sessionFactory.getCurrentSession().createQuery("select fbl from FacebookLike fbl where fbl.user.id=:userId");
		query.setParameter("userId", id.intValue());
		return new HashSet<FacebookLike>(query.list());
	}

	@Override
	public Set<FacebookLike> getUserFacebookLikesByFBId(Long id) {
		Query query = sessionFactory.getCurrentSession().createQuery("select fbl from FacebookLike fbl where fbl.fbUser.id=:fbId");
		query.setParameter("fbId", id);
		return new HashSet<FacebookLike>(query.list());
	}

	@Override
	public Set<FacebookLike> getUserFacebookLikesByEmail(String email) {
		Query query = sessionFactory.getCurrentSession().createQuery("select fbl from FacebookLike fbl where fbl.fbUser.emailId=:emailId");
		query.setParameter("emailId", email);
		return new HashSet<FacebookLike>(query.list());
	}

	@Override
	public void addFaceBookLike(FacebookLike fbLike) {
		sessionFactory.getCurrentSession().save(fbLike);
	}

	@Override
	public void updateFacebookLike(FacebookLike fbLike) {
		sessionFactory.getCurrentSession().update(fbLike);
	}
	
}
