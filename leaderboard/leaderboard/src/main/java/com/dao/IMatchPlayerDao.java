package com.dao;

import com.entity.MatchPlayer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMatchPlayerDao extends CrudRepository<MatchPlayer, Integer> {
	
}
