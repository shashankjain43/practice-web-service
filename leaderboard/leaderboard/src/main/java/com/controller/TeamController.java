package com.controller;

import com.entity.Team;
import com.request.CreateTeamRequest;
import com.response.CreateTeamResponse;
import com.response.ServiceResponse;
import com.service.ITeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/lb/team")
public class TeamController {

	@Autowired
	ITeamService teamService;

	@RequestMapping(value = "/create", produces = "application/JSON", method = RequestMethod.POST)
	public ServiceResponse<CreateTeamResponse> createTeam(@RequestBody CreateTeamRequest request) {
		Team team = teamService.createTeam(request);
		CreateTeamResponse res = new CreateTeamResponse();
		res.setTeamId(team.getId());
		ServiceResponse<CreateTeamResponse> response = new ServiceResponse<>();
		response.setResponse(res);
		return response;
	}
}
