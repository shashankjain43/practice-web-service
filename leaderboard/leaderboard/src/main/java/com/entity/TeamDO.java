package com.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "Team")
public class TeamDO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int teamId;
    String teamName;
    int userId;
    int matchId;
    int captainId;
    int vcaptainId;
    double totalScore;
    List<PlayerDO> players;

}
