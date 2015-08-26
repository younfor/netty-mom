
package com.alibaba.middleware.race.mom.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.nio.ByteBuffer;

import org.apache.log4j.Logger;

import com.alibaba.middleware.race.mom.broker.Broker;

public class NettyEncoder extends MessageToByteEncoder<NettyCommand> {
	private static Logger logger = Logger.getLogger(NettyEncoder.class);

    @Override
    public void encode(ChannelHandlerContext ctx, NettyCommand remotingCommand, ByteBuf out)
            throws Exception {
        try {
            ByteBuffer header = remotingCommand.encodeHeader();
            out.writeBytes(header);
            byte[] body = remotingCommand.getBody();
            if (body != null) {
                out.writeBytes(body);
            }
        } catch (Exception e) {
        	logger.error("encode exception:", e);
            if (remotingCommand != null) {
            	logger.error(remotingCommand.toString());
            }
            ctx.close();
        }
    }
}
