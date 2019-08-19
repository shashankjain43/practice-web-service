package com.snapdeal.ims.cache;

import java.util.Set;

import com.snapdeal.ims.dbmapper.entity.BlackList;
import com.snapdeal.ims.enums.EntityType;

/**
 * This class is used for caching all blacklisted entities.
 *
 * 
 *
 */
@Cache(name = "BlacklistCache")
public class BlacklistCache extends AbstractCache<EntityType,Set<String>> {
	
	public void remove(BlackList blackList)
	{
		Set<String> entities=this.get(blackList.getEntityType());
		if(entities==null)
			return;
		entities.remove(blackList.getEntity());
	}

}
