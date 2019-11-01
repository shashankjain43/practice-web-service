package com.request;

import lombok.Data;

import java.util.List;

@Data
public class CreateTeamRequest extends BaseRequest {

    int userId;
    int matchId;

    String teamName;
    int captainId;
    int vcaptainId;
    List<Integer> players;
}
