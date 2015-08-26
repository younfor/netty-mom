package com.alibaba.middleware.race.mom.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

public class NettyDecoder extends LengthFieldBasedFrameDecoder {
	private static Logger logger = Logger.getLogger(NettyDecoder.class);
    private static final int FRAME_MAX_LENGTH = 8388608;


    public NettyDecoder() {
        super(FRAME_MAX_LENGTH, 0, 4, 0, 4);
    }


    @Override
    public Object decode(ChannelHandlerContext ctx, ByteBuf in) throws Exception {
        ByteBuf frame = null;
        try {
            frame = (ByteBuf) super.decode(ctx, in);
            if (null == frame) {
                return null;
            }

            ByteBuffer byteBuffer = frame.nioBuffer();

            return NettyCommand.decode(byteBuffer);
        } catch (Exception e) {
        	logger.error("decode exception:", e);
            ctx.close();
        } finally {
            if (null != frame) {
                frame.release();
            }
        }

        return null;
    }
}
