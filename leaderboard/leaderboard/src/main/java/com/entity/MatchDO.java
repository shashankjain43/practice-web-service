package com.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Match")
public class MatchDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    String desc;
    String location;
    String opponent1;
    String opponent2;
}
