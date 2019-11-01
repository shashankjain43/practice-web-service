package com.dao;

import com.entity.TeamPlayer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamPlayerDao extends CrudRepository<TeamPlayer, Integer> {

    TeamPlayer findByPlayerIdAndTeamId(int playerId, int teamId);

}
