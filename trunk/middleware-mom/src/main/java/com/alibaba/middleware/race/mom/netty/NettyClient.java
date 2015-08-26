package com.alibaba.middleware.race.mom.netty;

import org.apache.log4j.Logger;

import com.alibaba.middleware.race.mom.broker.Broker;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

public class NettyClient {
	private static Logger logger = Logger.getLogger(NettyClient.class);
	// netty 参数
	private final Bootstrap bootstrap = new Bootstrap();
    private final EventLoopGroup eventLoopGroupWorker;
    private DefaultEventExecutorGroup defaultEventExecutorGroup;
    public NettyClient()
    {
    	this.eventLoopGroupWorker = new NioEventLoopGroup(1);
    	this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(8);	
    }
    public ChannelFuture creatChannel()
    {
        return this.bootstrap.connect("127.0.0.1",9999);
    }
    public void start()
    {
    	Bootstrap handler = this.bootstrap.group(this.eventLoopGroupWorker).channel(NioSocketChannel.class)//
                //
                .option(ChannelOption.TCP_NODELAY, true)
                //
                .option(ChannelOption.SO_KEEPALIVE, false)
                //
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(//
                            defaultEventExecutorGroup, //
                            new NettyEncoder(), //
                            new NettyDecoder(), //
                            new NettyClientHandler());
                    }
                });

    }
    public void processMessageReceived(ChannelHandlerContext ctx, NettyCommand msg)
    {
    	logger.debug("NettyClient收到信息");
    }
    class NettyClientHandler extends SimpleChannelInboundHandler<NettyCommand> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, NettyCommand msg) throws Exception {
            processMessageReceived(ctx, msg);

        }
    }
}
