package com.snapdeal.opspanel.promotion.bulkprocessortester;

import lombok.Data;

@Data
public class SampleResponse {
	
	private String teamOne;
	private String teamTwo;
	
	private int teamOneScore;
	private int teamTwoScore;
	
	
	public SampleResponse(String teamOne, String teamTwo, int teamOneScore,
			int teamTwoScore) {
		super();
		this.teamOne = teamOne;
		this.teamTwo = teamTwo;
		this.teamOneScore = teamOneScore;
		this.teamTwoScore = teamTwoScore;
	}


}
