package com.alibaba.middleware.race.mom.broker;

import java.net.InetSocketAddress;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.log4j.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

public class Broker {
	private static Logger logger = Logger.getLogger(Broker.class);  
	
    /**
     * 初始化
     */
	public Broker()
	{
		
	}
	/**
	 * 启动
	 * @param args
	 */
	public static void main(String args[])
	{
		Broker broker=new Broker();
	}
}
