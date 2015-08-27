package com.alibaba.middleware.race.mom.netty;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;
public class NettyCommand {

	/**
	 * header
	 */
	private byte type;
	/**
	 * body
	 */
	private transient byte[] body;

	/**
	 * 只打包Header，body部分独立传输
	 */
	public ByteBuffer encodeHeader() {
		final int bodyLength=(this.body != null ? this.body.length : 0);
		// 1> header length size
		int length = 1;

		// 2> header data length
		byte headerData = type;

		// 3> body data length
		length += bodyLength;

		ByteBuffer result = ByteBuffer.allocate(4 + length - bodyLength);

		// length
		result.putInt(length);

		// header data
		result.put(headerData);

		result.flip();

		return result;
	}
	public static NettyCommand decode(final ByteBuffer byteBuffer) {
        int length = byteBuffer.limit();
        byte headerData = byteBuffer.get();
        int bodyLength = length - 1;
        byte[] bodyData = null;
        if (bodyLength > 0) {
            bodyData = new byte[bodyLength];
            byteBuffer.get(bodyData);
        }

        NettyCommand cmd = new NettyCommand();
        cmd.setType(headerData);
        cmd.body = bodyData;

        return cmd;
    }
	public byte getType() {
		return type;
	}

	public void setType(byte type) {
		this.type = type;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}


}
