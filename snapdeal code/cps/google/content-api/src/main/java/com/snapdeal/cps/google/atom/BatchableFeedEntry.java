package com.snapdeal.cps.google.atom;

import com.google.api.client.util.Key;

/**
 * This class defines a feed entry for batch operations as specified by Google.
 * 
 * @author Sushant Taneja <sushant.taneja@snapdeal.com>
 * 
 */
public class BatchableFeedEntry extends FeedEntry {
    
    @Key("batch:operation")
    BatchOperation batchOperation = null;

    @Key("batch:id")
    String batchID = null;

    @Key("batch:status")
    BatchStatus batchStatus = null;

    @Key("batch:interrupted")
    BatchInterrupted batchInterrupted;

    public static class BatchOperation {
        @Key("@type")
        public String type;
    }

    public static class BatchStatus {
        @Key("@code")
        public int code;

        @Key("@reason")
        public String reason;
    }

    public static class BatchInterrupted {
        @Key("@error")
        public int error;

        @Key("@parsed")
        public int parsed;

        @Key("@reason")
        public String reason;

        @Key("@success")
        public int success;

        @Key("@unprocessed")
        public int unprocessed;
    }
}