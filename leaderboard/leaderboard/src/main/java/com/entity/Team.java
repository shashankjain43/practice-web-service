package com.entity;

import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "team")
@Builder
public class Team {

    @Id
    @GeneratedValue
    int id;
    String teamName;
    int userId;
    int matchId;
    int captainId;
    int vcaptainId;
    double totalScore;
    @OneToMany(mappedBy = "team")
    List<TeamPlayer> teamPlayers;

}
