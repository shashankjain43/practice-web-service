package com.request;

import lombok.Data;

@Data
public class GetLBSnapshotRequest extends BaseRequest {
    int matchId;
    int pageNumber;
    int pageSize;
}
