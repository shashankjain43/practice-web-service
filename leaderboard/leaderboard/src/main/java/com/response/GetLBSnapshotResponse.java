package com.response;

import com.model.LeaderBoard;
import lombok.Data;

import java.util.List;

@Data
public class GetLBSnapshotResponse extends BaseResponse {
    List<LeaderBoard> snapshot;
}
