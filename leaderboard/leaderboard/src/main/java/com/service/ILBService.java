package com.service;

import com.model.LeaderBoard;
import com.request.GetLBSnapshotRequest;

import java.util.List;

public interface ILBService {

    List<LeaderBoard> getLBSnapshot(GetLBSnapshotRequest request);
}
