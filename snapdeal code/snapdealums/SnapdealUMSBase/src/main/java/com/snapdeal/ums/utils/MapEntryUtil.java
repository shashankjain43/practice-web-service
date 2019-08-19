 /*
*  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 10-Jan-2013
*  @author naveen
*/
package com.snapdeal.ums.utils;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

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