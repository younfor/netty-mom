package com.alibaba.middleware.race.mom;

import org.apache.log4j.Logger;

import com.alibaba.middleware.race.mom.broker.Broker;
import com.alibaba.middleware.race.mom.netty.NettyClient;
import com.alibaba.middleware.race.mom.netty.NettyCommand;
import com.alibaba.middleware.race.mom.netty.NettyCommandType;
import com.alibaba.middleware.race.mom.netty.NettyOnReceiveListener;
import com.alibaba.middleware.race.mom.netty.NettySerializable;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public class DefaultProducer extends NettyClient implements Producer,NettyOnReceiveListener{
	private static Logger logger = Logger.getLogger(DefaultProducer.class); 
	private ChannelFuture producerChannelfuture;
	private String topic;
	private String groupId;
	public DefaultProducer(){
		// 设置监听
		this.nettyOnReceiveListener=this;
	}
	@Override
	public void start() {
		// netty start
		super.start();
		// create channel
		producerChannelfuture=super.creatChannel();
	}

	@Override
	public void setTopic(String topic) {
		this.topic=topic;
	}

	@Override
	public void setGroupId(String groupId) {
		this.groupId=groupId;
	}

	@Override
	public SendResult sendMessage(Message message) {
		SendResult sr=new SendResult();
		sr.setStatus(SendStatus.SUCCESS);
		NettyCommand nc=new NettyCommand();
		nc.setType(NettyCommandType.Producer2Broker);
		nc.setBody(NettySerializable.encode(message));
		System.out.println("开始发送");
		producerChannelfuture.channel().writeAndFlush(nc);
		return sr;
	}

	@Override
	public void asyncSendMessage(Message message, SendCallback callback) {
		
	}

	@Override
	public void stop() {
		super.close();
	}

	/**
	 * netty消息接收
	 */
	@Override
	public void processMessageReceived(ChannelHandlerContext ctx, NettyCommand msg) {
		logger.info("生产者收到消息");
	}

}
