package com.alibaba.middleware.race.mom.serial;

import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;



public class FastJsonSerial implements Serial{
    public String toJson() {
        return toJson(false);
    }


    public String toJson(final boolean prettyFormat) {
        return toJson(this, prettyFormat);
    }


    public static String toJson(final Object obj, boolean prettyFormat) {
        return JSON.toJSONString(obj, prettyFormat);
    }


    public static <T> T fromJson(String json, Class<T> classOfT) {
        return JSON.parseObject(json, classOfT);
    }


    public byte[] encode() {
        final String json = this.toJson();
        if (json != null) {
            return json.getBytes();
        }
        return null;
    }


    public byte[] encode(final Object obj) {
        final String json = toJson(obj, false);
        if (json != null) {
            return json.getBytes(Charset.forName("UTF-8"));
        }
        return null;
    }


    public  <T> T decode(final byte[] data, Class<T> classOfT) {
        final String json = new String(data, Charset.forName("UTF-8"));
        return fromJson(json, classOfT);
    }
}
