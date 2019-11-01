package com.dao;

import com.entity.Team;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITeamDao extends CrudRepository<Team, Integer> {
	
}
