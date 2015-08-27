package com.alibaba.middleware.race.mom;

import com.alibaba.middleware.race.mom.bean.RequestSubscribe;
import com.alibaba.middleware.race.mom.netty.NettyClient;
import com.alibaba.middleware.race.mom.netty.NettyCommand;
import com.alibaba.middleware.race.mom.netty.NettyCommandType;
import com.alibaba.middleware.race.mom.netty.NettyOnReceiveListener;
import com.alibaba.middleware.race.mom.serial.KryoSerial;
import com.alibaba.middleware.race.mom.serial.Serial;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;

public class DefaultConsumer extends NettyClient implements Consumer,NettyOnReceiveListener{

	//基本信息
	private String groupID;
	private String topic;
	private String filter;
	//回调
	private MessageListener listener;
	private ChannelFuture consumerChannel;
	private Serial serial=new KryoSerial();
	public DefaultConsumer(){
		// 设置监听
		super.nettyOnReceiveListener=this;
	}
	@Override
	public void start() {
		super.start();
		//绑定channel
		consumerChannel=super.creatChannel();
		//发送订阅信息
		RequestSubscribe rs=new RequestSubscribe(this.groupID,this.topic,this.filter);
		consumerChannel.channel().writeAndFlush(NettyCommand.buildNettyCommand(NettyCommandType.Consumer2BrokerSubscribe, serial.encode(rs)));
		
	}

	@Override
	public void subscribe(String topic, String filter, MessageListener listener) {
		this.topic=topic;
		this.filter=filter;
		this.listener=listener;
		
	}
	@Override
	public void setGroupId(String groupId) {
		this.groupID=groupId;
	}

	@Override
	public void stop() {
		super.close();
	}

	@Override
	public void processMessageReceived(ChannelHandlerContext ctx, NettyCommand msg) {
		// TODO Auto-generated method stub
		
	}

}
