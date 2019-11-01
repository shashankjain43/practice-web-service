package com.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue
    int id;
    String name;
    String desc;
    String location;
    String opponent1;
    String opponent2;
    @OneToMany(mappedBy = "match")
    List<MatchPlayer> matchPlayers;

}
