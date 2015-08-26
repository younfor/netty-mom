package com.alibaba.middleware.race.mom.netty;

import java.nio.ByteBuffer;
import java.util.concurrent.atomic.AtomicInteger;
public class NettyCommand {

	private static AtomicInteger RequestId = new AtomicInteger(0);
	/**
	 * header
	 */
	private int cmdId = RequestId.getAndIncrement();
	private NettyCommandType type;
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
		int length = 4;

		// 2> header data length
		byte[] headerData = NettySerializable.encode(this);
		length += headerData.length;

		// 3> body data length
		length += bodyLength;

		ByteBuffer result = ByteBuffer.allocate(4 + length - bodyLength);

		// length
		result.putInt(length);

		// header length
		result.putInt(headerData.length);

		// header data
		result.put(headerData);

		result.flip();

		return result;
	}
	public static NettyCommand decode(final ByteBuffer byteBuffer) {
        int length = byteBuffer.limit();
        int headerLength = byteBuffer.getInt();

        byte[] headerData = new byte[headerLength];
        byteBuffer.get(headerData);

        int bodyLength = length - 4 - headerLength;
        byte[] bodyData = null;
        if (bodyLength > 0) {
            bodyData = new byte[bodyLength];
            byteBuffer.get(bodyData);
        }

        NettyCommand cmd = NettySerializable.decode(headerData, NettyCommand.class);
        cmd.body = bodyData;

        return cmd;
    }
	public NettyCommandType getType() {
		return type;
	}

	public void setType(NettyCommandType type) {
		this.type = type;
	}

	public byte[] getBody() {
		return body;
	}

	public void setBody(byte[] body) {
		this.body = body;
	}

	public int getCmdId() {
		return cmdId;
	}

}
