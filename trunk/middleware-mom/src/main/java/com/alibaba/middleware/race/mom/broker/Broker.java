package com.alibaba.middleware.race.mom.broker;

import org.apache.log4j.Logger;
import com.alibaba.middleware.race.mom.Message;
import com.alibaba.middleware.race.mom.bean.RequestSubscribe;
import com.alibaba.middleware.race.mom.netty.NettyCommand;
import com.alibaba.middleware.race.mom.netty.NettyCommandType;
import com.alibaba.middleware.race.mom.netty.NettyOnReceiveListener;
import com.alibaba.middleware.race.mom.netty.NettyServer;
import com.alibaba.middleware.race.mom.serial.KryoSerial;
import com.alibaba.middleware.race.mom.serial.Serial;
import io.netty.channel.ChannelHandlerContext;

public class Broker extends NettyServer implements NettyOnReceiveListener{
	private static Logger logger = Logger.getLogger(Broker.class);  
	private Serial serial=new KryoSerial();
    /**
     * 初始化
     */
	public Broker()
	{
		logger.info("启动broker");
		// 设置监听
		super.nettyOnReceiveListener=this;
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
		logger.info("borker get msg "+msg.getType());
		switch(msg.getType())
		{
		case NettyCommandType.Producer2Broker:
			logger.info(msg.getBody().length);
			Message mes=serial.decode(msg.getBody(), Message.class);
			logger.info(new String(mes.getBody()));
			break;
		case NettyCommandType.Consumer2BrokerSubscribe:
			RequestSubscribe requestSub=serial.decode(msg.getBody(), RequestSubscribe.class);
			logger.info("sub:"+requestSub.getTopic());
			break;
		}
	}
}
