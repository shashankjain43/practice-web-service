package com.controller;

import com.model.LeaderBoard;
import com.request.AddPlayersToMatchRequest;
import com.request.GetLBSnapshotRequest;
import com.response.AddPlayersToMatchResponse;
import com.response.GetLBSnapshotResponse;
import com.response.ServiceResponse;
import com.service.ILBService;
import com.service.IMatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/service/lb")
public class LBController {

	@Autowired
	ILBService lbService;

	@RequestMapping(value = "/snapshot", produces = "application/JSON", method = RequestMethod.POST)
	public ServiceResponse<GetLBSnapshotResponse> getLeaderboardSnapshot(@RequestBody GetLBSnapshotRequest request) {
		List<LeaderBoard> snapshot = lbService.getLBSnapshot(request);
		GetLBSnapshotResponse res = new GetLBSnapshotResponse();
		res.setSnapshot(snapshot);
		ServiceResponse<GetLBSnapshotResponse> response = new ServiceResponse<>();
		response.setResponse(res);
		return response;
	}
}
