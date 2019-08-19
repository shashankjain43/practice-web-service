package com.snapdeal.ims.response;

import java.io.Serializable;

import lombok.Data;

@Data
public abstract class AbstractResponse implements Serializable {

	private static final long serialVersionUID = 7732411244235959766L;

   protected String startTime;

   protected String endTime;
}