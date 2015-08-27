package com.alibaba.middleware.race.mom.serial;

public interface Serial {
	public  byte[] encode(final Object obj);
	public  <T> T decode(final byte[] data, Class<T> classOfT);
}
