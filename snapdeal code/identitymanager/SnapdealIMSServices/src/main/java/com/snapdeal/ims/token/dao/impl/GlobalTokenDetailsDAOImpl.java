//package com.snapdeal.ims.token.dao.impl;
//
//import java.util.List;
//
//import org.mybatis.spring.SqlSessionTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.snapdeal.ims.token.dao.IGlobalTokenDetailsDAO;
//import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
//import com.snapdeal.payments.metrics.annotations.Logged;
//import com.snapdeal.payments.metrics.annotations.Marked;
//import com.snapdeal.payments.metrics.annotations.Timed;
//
//
//@Transactional(readOnly = true)
//public class GlobalTokenDetailsDAOImpl implements IGlobalTokenDetailsDAO {
//
//	@Autowired
//	private SqlSessionTemplate sqlSession;
//
//	@Override
//	@Transactional(readOnly = false)
//	@Timed
//	@Marked
//	@Logged
//	public void save(GlobalTokenDetailsEntity globalTokenDetailsEntity) {
//		sqlSession.insert("GlobalTokenDetails.save", globalTokenDetailsEntity);
//	}
//
//	@Override
//	@Timed
//	@Marked
//	@Logged
//	public GlobalTokenDetailsEntity getGlobalTokenById(String globalTokenId) {
//		GlobalTokenDetailsEntity entity = new GlobalTokenDetailsEntity();
//		entity.setGlobalTokenId(globalTokenId);
//		List<GlobalTokenDetailsEntity> globalTokens = sqlSession.selectList(
//				"GlobalTokenDetails.fetchById", entity);
//		if (null != globalTokens && !globalTokens.isEmpty()) {
//			return globalTokens.get(0);
//		}
//		return null;
//	}
//
//	@Override
//	@Timed
//	@Marked
//	@Logged
//	public void delete(String globalTokenId) {
//		sqlSession.delete("GlobalTokenDetails.deleteById", globalTokenId);
//	}
//
//	@Override
//	@Timed
//	@Marked
//	@Logged
//	public void deleteAllTokenForUser(String userId) {
//		sqlSession.delete("GlobalTokenDetails.deleteAllTokenForUser", userId);
//	}
//
//	@Override
//	@Timed
//	@Marked
//	@Logged
//	public void updateExpiryDate(GlobalTokenDetailsEntity globalTokenDetailsEntity) {
//		sqlSession.update("GlobalTokenDetails.updateExpiryDate", globalTokenDetailsEntity);
//	}
//}