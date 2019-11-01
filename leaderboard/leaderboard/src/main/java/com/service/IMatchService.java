package com.service;

import com.request.AddPlayersToMatchRequest;

public interface IMatchService {

    boolean addPlayersToMatch(AddPlayersToMatchRequest request);
}
