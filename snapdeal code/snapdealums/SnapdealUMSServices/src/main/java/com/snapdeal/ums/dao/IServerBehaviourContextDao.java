package com.snapdeal.ums.dao;

import com.snapdeal.ums.core.entity.DisabledURL;
import com.snapdeal.ums.core.entity.ServerBehaviourContext;

public interface IServerBehaviourContextDao {

	public ServerBehaviourContext getServerBehaviourContext(
			String disabledServersProfile);

	public DisabledURL getDisabledServiceURL(String url);

	public ServerBehaviourContext updateContext(
			ServerBehaviourContext serverBehaviourContext);

//	public DisabledURL updateDisabledURL(DisabledURL disabledURL);
//
//	public void persistDisabledURL(DisabledURL disabledURL);

}
