package com.snapdeal.ums.services.facebook.impl;

import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.core.entity.facebook.FacebookLike;
import com.snapdeal.ums.dao.facebook.IFacebookLikesDao;
import com.snapdeal.ums.services.facebook.IFacebookLikeServiceInternal;
/**
 * 
 * @author fanendra
 *
 */
@Service("fbLikeServiceInternal")
@Transactional
public class FacebookLikeServiceInternalImpl implements IFacebookLikeServiceInternal{

	@Autowired
	private IFacebookLikesDao facebookLikesDao;
	
	@Override
	public Set<FacebookLike> getUserFacebookLikesBySDId(Long id) {
		return facebookLikesDao.getUserFacebookLikesBySDId(id);
	}

	@Override
	public Set<FacebookLike> getUserFacebookLikesByFBId(Long id) {
		return facebookLikesDao.getUserFacebookLikesByFBId(id);
	}

	@Override
	public Set<FacebookLike> getUserFacebookLikesByEmail(String email) {
		return facebookLikesDao.getUserFacebookLikesByEmail(email);
	}

	@Override
	public void addFaceBookLike(FacebookLike fbLike) {
		facebookLikesDao.addFaceBookLike(fbLike);
	}

	@Override
	public void updateFacebookLike(FacebookLike fbLike) {
		facebookLikesDao.updateFacebookLike(fbLike);
	}

}
