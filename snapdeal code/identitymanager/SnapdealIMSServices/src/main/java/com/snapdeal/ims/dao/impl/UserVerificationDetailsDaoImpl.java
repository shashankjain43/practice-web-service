//package com.snapdeal.ims.dao.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
//import com.snapdeal.ims.dbmapper.IUserVerificationDetailsMapper;
//import com.snapdeal.ims.dbmapper.entity.UserVerification;
//
//@Component
//@Transactional("transactionManager")
//public class UserVerificationDetailsDaoImpl implements IUserVerificationDetailsDao{
//	
//	@Autowired
//	private IUserVerificationDetailsMapper userVerificationDetailsMapper;
//	
//	@Override
//	public void create(UserVerification userVerificationEntity) {
//		userVerificationDetailsMapper.create(userVerificationEntity);
//	}
//
//	@Override
//	public UserVerification getUserVerificationDetailsByCode(String code) {
//		return userVerificationDetailsMapper.getUserVerificationDetailsByCode(code);
//	}
//
//}
