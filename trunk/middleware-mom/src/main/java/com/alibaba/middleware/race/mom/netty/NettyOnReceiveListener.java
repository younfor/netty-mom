package com.alibaba.middleware.race.mom.netty;

import io.netty.channel.ChannelHandlerContext;

public interface NettyOnReceiveListener {
	public void processMessageReceived(ChannelHandlerContext ctx, NettyCommand msg);
}
