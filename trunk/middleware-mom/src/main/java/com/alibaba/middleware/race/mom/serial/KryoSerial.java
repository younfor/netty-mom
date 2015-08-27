package com.alibaba.middleware.race.mom.serial;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;

public class KryoSerial implements Serial{

	private final Kryo kryo;
	public KryoSerial() {
		kryo=new Kryo();
	}
	@Override
	public byte[] encode(Object obj) {
		Output output = null; 
        output = new Output(1, 4096); 
        kryo.writeObject(output, obj); 
        byte[] data = output.toBytes(); 
        output.flush(); 
        return data;
	}

	@Override
	public <T> T decode(byte[] data, Class<T> classOfT) {
		Input input = null; 
        input = new Input(data); 
        T res = kryo.readObject(input, classOfT);
        input.close();
        return res;
	}

}
