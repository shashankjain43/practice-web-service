package com.snapdeal.ums.core.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserEntity {

    @Id
    private int id;
  
    private String userName;
    private String password;
    
}