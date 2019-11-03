package com.service;

import com.dao.IMatchDao;
import com.dao.IMatchPlayerDao;
import com.dao.ITeamDao;
import com.dao.ITeamPlayerDao;
import com.entity.Match;
import com.entity.MatchPlayer;
import com.entity.Team;
import com.entity.TeamPlayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Random;

@Component
public class ScoreBoard {

    @Autowired
    private IMatchPlayerDao matchPlayerDao;

    @Autowired
    private IMatchDao matchDao;

    @Autowired
    private ITeamDao teamDao;

    @Autowired
    private ITeamPlayerDao teamPlayerDao;


    private static final Random random = new Random();
    private static final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Scheduled(fixedDelay = 30 * 1000)
    @Transactional
    public void updateScore() {

        //Printing date
        Date now = new Date();
        String nowString = df.format(now);
        System.out.println("Now is: " + nowString);

        List<Match> matches = (List<Match>) matchDao.findAll();
        for (Match match : matches) {
            List<MatchPlayer> matchPlayers = match.getMatchPlayers();
            if (Objects.nonNull(matchPlayers) && !matchPlayers.isEmpty()) {
                MatchPlayer randomMatchPlayer = matchPlayers.get(random.nextInt(matchPlayers.size() - 1));
                double randomScore = random.nextDouble();
                randomMatchPlayer.setScore(randomMatchPlayer.getScore() + randomScore);
                matchPlayerDao.save(randomMatchPlayer);
                List<Team> teams = teamDao.findByMatchId(match.getId());
                if (Objects.nonNull(teams)) {
                    for (Team team : teams) {
                        TeamPlayer teamPlayer = teamPlayerDao.findByPlayerIdAndTeamId(randomMatchPlayer.getPlayer().getId(), team.getId());
                        if (Objects.nonNull(teamPlayer)) {
                            teamPlayer.setScore(randomMatchPlayer.getScore());
                            teamPlayerDao.save(teamPlayer);
                            team.setTotalScore(team.getTotalScore() + randomScore);
                            teamDao.save(team);
                        }
                    }
                }
            }
        }
    }
}