package com.entity;


import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "profiles")
@Data
public class ProfileDO implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int Id;

    @Column
    String name;
    String dob;

    @Column(unique = true)
    String mobile;

    @Column(unique = true)
    String email;
}
