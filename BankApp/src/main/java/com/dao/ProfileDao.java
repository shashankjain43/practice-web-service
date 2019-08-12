package com.dao;

import com.entity.ProfileDO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileDao extends CrudRepository<ProfileDO, Integer> {
    boolean existsByMobile(String mobile);
    boolean existsByEmail(String email);
}
