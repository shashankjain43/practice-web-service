package com.snapdeal.bulkprocess.factory;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.registration.IBulkFileRegistration;

@Component
public class BulkRegistrationFactory {
	
	private static Map<String, IBulkFileRegistration> bulkRegistrationMap = new HashedMap();
	
	@Autowired
	private List<IBulkFileRegistration> allbulkRegistrationsList;
	
	@PostConstruct
	public void buildMap()
	{
		for (IBulkFileRegistration iBulkFileRegistration : allbulkRegistrationsList) {
			bulkRegistrationMap.put(iBulkFileRegistration.getBulkActivityId(), iBulkFileRegistration);
		}
	} 
	public IBulkFileRegistration getIBulkFileRegistrationInfo(String bulkActivityId) {
		IBulkFileRegistration iBulkFileRegistration = null;
		if (isValidBulkActivityId(bulkActivityId))
			iBulkFileRegistration = (IBulkFileRegistration)bulkRegistrationMap.get(bulkActivityId);
		return iBulkFileRegistration;
	}
	public boolean isValidBulkActivityId(String taskType) {
		if (bulkRegistrationMap.containsKey(taskType)) {
			return true;
		}
		return false;
	}
}
