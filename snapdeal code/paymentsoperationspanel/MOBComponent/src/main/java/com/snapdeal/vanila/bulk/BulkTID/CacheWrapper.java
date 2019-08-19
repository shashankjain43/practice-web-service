package com.snapdeal.vanila.bulk.BulkTID;

import java.util.HashSet;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CacheWrapper {
	
	private HashSet<CustomTID> hset;
	
	@JsonProperty
	private boolean success;
	
	private String error;

}
