 /*
*  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 25-Jan-2013
*  @author naveen
*/
package com.snapdeal.core.sro.email;

import com.dyuproject.protostuff.Tag;

public class TemplateParam<E> {
    /**
 * 
 */
    private static final long serialVersionUID = 2135114339986122029L;
    @Tag(9)
    String                    Key;
    @Tag(10)
    E                    value;

    public TemplateParam(String key, E value) {
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

    public Object getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }
    
}