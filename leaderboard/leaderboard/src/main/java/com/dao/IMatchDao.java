package com.dao;

import com.entity.Match;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMatchDao extends CrudRepository<Match, Integer> {
	
}
