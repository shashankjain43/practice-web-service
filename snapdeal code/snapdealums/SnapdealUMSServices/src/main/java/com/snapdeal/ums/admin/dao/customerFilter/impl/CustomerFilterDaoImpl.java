package com.snapdeal.ums.admin.dao.customerFilter.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.CustomerFilter;
import com.snapdeal.ums.core.entity.CustomerFilter.FilterDomain;
import com.snapdeal.ums.admin.dao.customerFilter.ICustomerFilterDao;

@Repository("umsCustomerFilterDaoImpl")
public class CustomerFilterDaoImpl implements ICustomerFilterDao {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public CustomerFilter getCustomerFilterById(int id) {
		Query q = sessionFactory.getCurrentSession().createQuery("from CustomerFilter where id=:id");
		q.setParameter("id", id);
		return (CustomerFilter) q.uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerFilter> getAllCustomerFilters() {
		Query q = sessionFactory.getCurrentSession().createQuery("from CustomerFilter");
		return q.list();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<CustomerFilter> getCustomerFiltersByDomain(
			CustomerFilter.FilterDomain domain) {

		Query q = sessionFactory.getCurrentSession().createQuery(
				"from CustomerFilter where filterDomain=:domain");
		q.setParameter("domain", domain);
		return q.list();

	}
	
	@Override
	public CustomerFilter getCustomerFilterByName(String name, FilterDomain domain) {
		
		Query q = sessionFactory.getCurrentSession().createQuery(
				"from CustomerFilter where filterDomain=:domain and name=:name");
		q.setParameter("domain", domain).setParameter("name", name);
		return (CustomerFilter) q.uniqueResult();
	}
	
	@Override
	public void persist(CustomerFilter customerFilter) {
		sessionFactory.getCurrentSession().persist(customerFilter);
	}
}
