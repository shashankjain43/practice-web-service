package com.service;

import com.dao.IMatchDao;
import com.dao.IMatchPlayerDao;
import com.dao.IPlayerDao;
import com.entity.Match;
import com.entity.MatchPlayer;
import com.request.AddPlayersToMatchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MatchServiceImpl implements IMatchService {

    @Autowired
    private IMatchDao matchDao;

    @Autowired
    private IMatchPlayerDao matchPlayerDao;

    @Autowired
    private IPlayerDao playerDao;

    @Override
    public boolean addPlayersToMatch(AddPlayersToMatchRequest request) {
        Match match = matchDao.findById(request.getMatchId()).get();
        for (int i: request.getPlayers()) {
            MatchPlayer matchPlayer = new MatchPlayer();
            matchPlayer.setMatch(match);
            matchPlayer.setPlayer(playerDao.findById(i).get());
            matchPlayerDao.save(matchPlayer);
        }
        return true;
    }
}
