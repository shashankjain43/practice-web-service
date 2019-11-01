package com.service;

import com.entity.Team;
import com.request.CreateTeamRequest;

public interface ITeamService {

    Team createTeam(CreateTeamRequest request);
}
