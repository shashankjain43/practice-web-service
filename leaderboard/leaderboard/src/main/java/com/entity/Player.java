package com.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue
    int id;
    String name;
    String playingStyleDesc;
    double score;
}
