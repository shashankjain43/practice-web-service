package com.snapdeal.opspanel.userpanel.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Joiner;
import com.snapdeal.ims.entity.Activity;
import com.snapdeal.ims.response.GetActivityResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;

import lombok.Data;

@Data
public class ActivityLogUtils {
	
	public static void validateDates(String startDate , String endDate , int difference) throws OpsPanelException{
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setLenient(false);
			Date sdate= formatter.parse(startDate);
			Date edate = formatter.parse(endDate);
			if(edate.getTime()<sdate.getTime()){
				throw new OpsPanelException("AV-1002", "End Date must be greater than Start Date");
			}
			int days =  (int) ((edate.getTime()- sdate.getTime())/ (1000 * 60 * 60 * 24)) ;
			if(days>difference){
				throw new OpsPanelException("AV-1003", "Difference betwen start and end Date can be max"+ difference +"days.");
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			throw new OpsPanelException("AV-1004", "Dates must be in the format yyyy-MM-dd. Please Check.");
		}
	}
	
	public static StringBuffer getBufferForCSV(GetActivityResponse response){
		String[] header =  {"id","activityType","activitySubtype","status","ipAddress","entityId","macAddress","clientId","createdDate","serverIpAddress","tableName","userAgent"};	
		
		StringBuffer sb = new StringBuffer();
		sb.append(Joiner.on(",").join(header));
		sb.append("\r\n");
		
		for(Activity activity : response.getActivities()){
			sb.append(activity.getId());
			sb.append(",");
			sb.append(activity.getActivityType());
			sb.append(",");
			sb.append(activity.getActivitySubtype());
			sb.append(",");
			sb.append(activity.getStatus());
			sb.append(",");
			sb.append(activity.getIpAddress());
			sb.append(",");
			sb.append(activity.getEntityId());
			sb.append(",");
			sb.append(activity.getMacAddress());
			sb.append(",");
			sb.append(activity.getClientId());
			sb.append(",");
			sb.append(activity.getCreatedDate());
			sb.append(",");
			sb.append(activity.getServerIpAddress());
			sb.append(",");
			sb.append(activity.getTableName());
			sb.append(",");
			sb.append(activity.getUserAgent());
			sb.append(",");
			sb.append("\r\n");
		}
		
		return sb;
		
	}

	
}
