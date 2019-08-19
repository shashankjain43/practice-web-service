/*
 * Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved. JASPER
 * INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package com.snapdeal.ums.event;

import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.ProtostuffIOUtil;
import com.dyuproject.protostuff.Schema;
import com.dyuproject.protostuff.runtime.RuntimeSchema;

/**
 * Utility class for serializing and de-serializing objects using protobuff.
 * 
 */
public class ProtoBufferWiringTool
{

    public static <T> byte[] serialize(T serializableObj, Class<T> objectClass)
    {

        LinkedBuffer buffer = LinkedBuffer.allocate(2048);
        Schema<T> schema = RuntimeSchema.getSchema(objectClass);
        try {
            return ProtostuffIOUtil.toByteArray(serializableObj, schema, buffer);
        }
        finally {
            buffer.clear();
        }
    }

    public static <T> T deserialize(byte[] bytes, T obj, Class<T> clazz)
    {

        ProtostuffIOUtil.mergeFrom(bytes, obj, RuntimeSchema.getSchema(clazz));
        return obj;
    }
}
