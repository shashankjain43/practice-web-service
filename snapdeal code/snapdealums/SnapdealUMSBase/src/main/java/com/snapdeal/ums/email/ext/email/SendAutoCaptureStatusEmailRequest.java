
package com.snapdeal.ums.email.ext.email;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendAutoCaptureStatusEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6854788485676630770L;
	@Tag(3)
    private String pg;
    @Tag(4)
    private String date;
    @Tag(5)
    private List<MapEntryUtil> failedOrders = new ArrayList<SendAutoCaptureStatusEmailRequest.MapEntryUtil>();

    public SendAutoCaptureStatusEmailRequest() {
    }

    public SendAutoCaptureStatusEmailRequest(String pg, String date, Map<String, String> failedOrders) {
        super();
        this.pg = pg;
        this.date = date;
        this.setFailedOrders(failedOrders);
    }

    public String getPg() {
        return pg;
    }

    public void setPg(String pg) {
        this.pg = pg;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<MapEntryUtil> getFailedOrders() {
        return failedOrders;
    }

    public void setFailedOrders(Map<String, String> failedOrders) {
        if (failedOrders == null)
            return;
        for (Entry<String, String> entry : failedOrders.entrySet())
            this.failedOrders.add(new MapEntryUtil(entry.getKey(), entry.getValue()));
    }
    
    public class MapEntryUtil implements Serializable {
        /**
     * 
     */
        private static final long serialVersionUID = 2135114339986122029L;
        @Tag(1)
        String                    Key;
        @Tag(2)
        String                    value;

        public MapEntryUtil(String key, String value) {
            super();
            this.Key = key;
            this.value = value;
        }

        public String getKey() {
            return Key;
        }

        public void setKey(String key) {
            Key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }
}
