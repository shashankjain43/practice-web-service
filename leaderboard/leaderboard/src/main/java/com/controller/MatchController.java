package com.controller;

import com.request.AddPlayersToMatchRequest;
import com.response.AddPlayersToMatchResponse;
import com.response.ServiceResponse;
import com.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/lb/match")
public class MatchController {

	@Autowired
	IMatchService matchService;

	@RequestMapping(value = "/addPlayers", produces = "application/JSON", method = RequestMethod.POST)
	public ServiceResponse<AddPlayersToMatchResponse> addPlayersToMatch(@RequestBody AddPlayersToMatchRequest request) {
		matchService.addPlayersToMatch(request);
		AddPlayersToMatchResponse res = new AddPlayersToMatchResponse();
		res.setSuccess(true);
		ServiceResponse<AddPlayersToMatchResponse> response = new ServiceResponse<>();
		response.setResponse(res);
		return response;
	}
}
