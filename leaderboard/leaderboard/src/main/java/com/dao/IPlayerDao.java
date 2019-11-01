package com.dao;

import com.entity.Player;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPlayerDao extends CrudRepository<Player, Integer> {
	
}
