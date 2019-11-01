package com.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name="team_player")
@Data
public class TeamPlayer {

    @Id
    @GeneratedValue
    int id;

    @ManyToOne
    Team team;

    @ManyToOne
    Player player;

    double score;

}
