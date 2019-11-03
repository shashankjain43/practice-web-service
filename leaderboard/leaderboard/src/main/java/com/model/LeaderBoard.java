package com.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class LeaderBoard implements Serializable {

    int matchId;
    int teamId;
    double totalScore;
    int rank;
}
