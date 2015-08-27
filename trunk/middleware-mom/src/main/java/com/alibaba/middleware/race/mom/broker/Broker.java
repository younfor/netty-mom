package com.alibaba.middleware.race.mom.broker;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;

import com.alibaba.middleware.race.mom.netty.NettyCommand;
import com.alibaba.middleware.race.mom.netty.NettyOnReceiveListener;
import com.alibaba.middleware.race.mom.netty.NettyServer;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

public class Broker extends NettyServer implements NettyOnReceiveListener{
	private static Logger logger = Logger.getLogger(Broker.class);  
	
    /**
     * 初始化
     */
	public Broker()
	{
		logger.info("启动broker");
		// 设置监听
		this.nettyOnReceiveListener=this;
		super.start();
	}

	/**
	 * 启动
	 * @param args
	 */
	public static void main(String args[])
	{
		Broker broker=new Broker();
	}
	/**
	 * 收到信息回调
	 */
	@Override
	public void processMessageReceived(ChannelHandlerContext ctx, NettyCommand msg) {
		logger.info("borker get msg "+msg.getBody());
		
	}
}
