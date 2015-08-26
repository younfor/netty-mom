package com.alibaba.middleware.race.mom;

import com.alibaba.middleware.race.mom.netty.NettyClient;
import com.alibaba.middleware.race.mom.netty.NettyCommand;
import com.alibaba.middleware.race.mom.netty.NettyCommandType;
import com.alibaba.middleware.race.mom.netty.NettySerializable;

import io.netty.channel.ChannelFuture;

public class DefaultProducer extends NettyClient implements Producer{

	private ChannelFuture producerChannelfuture;
	private String topic;
	private String groupId;
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

}
