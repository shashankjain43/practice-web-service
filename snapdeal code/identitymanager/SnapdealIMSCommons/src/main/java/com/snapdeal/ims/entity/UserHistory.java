package com.snapdeal.ims.entity;

import java.io.Serializable;
import java.sql.Timestamp;
import com.snapdeal.ims.enums.UpdatedFeild;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserHistory implements Serializable{
	private static final long serialVersionUID = -3439313783078356708L;
	private String userId;
	private UpdatedFeild field;
	private String oldValue;
	private String newValue;
	private Timestamp updatedTime;
}