package com.snapdeal.ims.client.dbmapper.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import com.snapdeal.ims.client.dbmapper.entity.info.ActivityStatus;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Activity implements Serializable {

	private static final long serialVersionUID = 8388632424919159650L;
	private long id;
	private String activityType;
	private String activitySubtype;
	private ActivityStatus status;
	private String ipAddress;
	private String entityId;
	private String macAddress;
	private String clientId;
	private Date createdDate;
	private String serverIpAddress;
	private String tableName;
}
