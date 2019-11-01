package com.service;

import com.dao.IPlayerDao;
import com.dao.ITeamDao;
import com.dao.ITeamPlayerDao;
import com.entity.Team;
import com.entity.TeamPlayer;
import com.request.CreateTeamRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeamServiceImpl implements ITeamService {

    @Autowired
    private ITeamDao teamDao;

    @Autowired
    private IPlayerDao playerDao;

    @Autowired
    private ITeamPlayerDao teamPlayerDao;


    @Override
    public Team createTeam(CreateTeamRequest request) {
        Team team = Team.builder()
                .teamName(request.getTeamName())
                .userId(request.getUserId())
                .matchId(request.getMatchId())
                .captainId(request.getCaptainId())
                .vcaptainId(request.getVcaptainId())
                //.players((List<Player>) playerDao.findAllById(request.getPlayers()))
                .build();
        team = teamDao.save(team);
        for (int i : request.getPlayers()) {
            TeamPlayer tp = new TeamPlayer();
            tp.setTeam(team);
            tp.setPlayer(playerDao.findById(i).get());
            teamPlayerDao.save(tp);
        }
        return team;
    }
}
