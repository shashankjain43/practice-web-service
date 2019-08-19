package com.snapdeal.ums.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.entity.DisabledURL;
import com.snapdeal.ums.core.entity.SDCashBackProgramConfig;
import com.snapdeal.ums.core.entity.ServerBehaviourContext;
import com.snapdeal.ums.core.entity.User;

@Repository
public class SDCashBackProgramConfigDaoImpl implements
		ISDCashBackProgramConfigDao {

	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public List<SDCashBackProgramConfig> getAllSDCashBackProgramConfig() {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from SDCashBackProgramConfig order by lastProcessed desc");
		query.setMaxResults(100);
		return query.list();
	}
	
	@Override
	public SDCashBackProgramConfig updateConfig(SDCashBackProgramConfig config) {
		return (SDCashBackProgramConfig) sessionFactory.getCurrentSession().merge(config);
	}

	@Override
	public int addSDCashBackProgramConfig(SDCashBackProgramConfig config) {
		config.setCreated(DateUtils.getCurrentTime());
		config.setUpdated(DateUtils.getCurrentTime());
		return (Integer) sessionFactory.getCurrentSession().save(config);
	}

	@Override
	public SDCashBackProgramConfig getSDCashBackProgramConfigById(int configId) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from SDCashBackProgramConfig where id=:configId");
		query.setParameter("configId", configId);
		return (SDCashBackProgramConfig) query.uniqueResult();
	}

	@Override
	public int deleteSDCashBackProgramConfigById(int configId) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"delete from SDCashBackProgramConfig where id=:configId");
		query.setParameter("configId", configId);
		return query.executeUpdate();
	}

	@Override
	public void disableSDCashBackProgramConfigById(int configId) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from SDCashBackProgramConfig where id=:configId");
		query.setParameter("configId", configId);
		SDCashBackProgramConfig config = (SDCashBackProgramConfig) query
				.uniqueResult();
		config.setEnabled(false);
	}

	@Override
	public void enableSDCashBackProgramConfigById(int configId) {
		Query query = sessionFactory.getCurrentSession().createQuery(
				"from SDCashBackProgramConfig where id=:configId");
		query.setParameter("configId", configId);
		SDCashBackProgramConfig config = (SDCashBackProgramConfig) query
				.uniqueResult();
		config.setEnabled(true);
	}

}
