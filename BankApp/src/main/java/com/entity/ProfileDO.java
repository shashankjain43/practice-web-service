package com.entity;


import javax.persistence.*;

@Entity
@Table(name = "profiles")
public class ProfileDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int Id;
    
    String name;
    String dob;
    String mobile;
    String email;
}
