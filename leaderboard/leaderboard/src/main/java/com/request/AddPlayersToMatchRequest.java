package com.request;

import lombok.Data;

import java.util.List;

@Data
public class AddPlayersToMatchRequest extends BaseRequest {

    int matchId;
    List<Integer> players;
}
