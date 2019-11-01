package com.dao;

import com.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITeamDao extends CrudRepository<Team, Integer> {

    List<Team> findByMatchId(int matchId);
}
