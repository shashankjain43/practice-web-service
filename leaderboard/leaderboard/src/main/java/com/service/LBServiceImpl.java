package com.service;

import com.model.LeaderBoard;
import com.request.GetLBSnapshotRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LBServiceImpl implements ILBService {

    @Override
    public List<LeaderBoard> getLBSnapshot(GetLBSnapshotRequest request) {
        return null;
    }
}
