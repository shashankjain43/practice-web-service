package com.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="match_player")
@Data
public class MatchPlayer {

    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    Match match;

    @ManyToOne
    Player player;

    double score;

}
