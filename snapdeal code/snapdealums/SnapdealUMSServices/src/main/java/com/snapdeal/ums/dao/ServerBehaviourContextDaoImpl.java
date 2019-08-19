package com.snapdeal.ums.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.DisabledURL;
import com.snapdeal.ums.core.entity.ServerBehaviourContext;




@Repository
public class ServerBehaviourContextDaoImpl implements IServerBehaviourContextDao{
	
	
	private SessionFactory sessionFactory;
	
	 @Autowired
	    public void setSessionFactory(SessionFactory sessionFactory) {
	        this.sessionFactory = sessionFactory;
	    }
	 
	    @Override
		public ServerBehaviourContext getServerBehaviourContext(String name) {
			 Query query = sessionFactory.getCurrentSession().createQuery("from ServerBehaviourContext where name=:name");
			 query.setParameter("name", name);
			return (ServerBehaviourContext) query.uniqueResult();
		}
	    @Override
	    public DisabledURL getDisabledServiceURL(String url){
	    	Query query=sessionFactory.getCurrentSession().createQuery("from DisabledURL where url=:url");
	    	query.setParameter("url", url);
			return (DisabledURL) query.uniqueResult();
	    
	    	
	    }
	    
//	    @Override
//	    public DisabledURL updateDisabledURL(DisabledURL disabledURL){
//	        return (DisabledURL) sessionFactory.getCurrentSession().merge(disabledURL);
//	    }
//	    
//	    @Override
//	        public void persistDisabledURL(DisabledURL disabledURL){
//	        sessionFactory.getCurrentSession().persist(disabledURL);
//	        }
	    
	    

		@Override
		public ServerBehaviourContext updateContext(
				ServerBehaviourContext serverBehaviourContext) {
			
			return (ServerBehaviourContext) sessionFactory.getCurrentSession().merge(serverBehaviourContext);
		}

}
