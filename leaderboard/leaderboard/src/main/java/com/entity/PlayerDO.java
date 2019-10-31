package com.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "Player")
public class PlayerDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int id;
    String name;
    String playingStyleDesc;
    double score;
}
