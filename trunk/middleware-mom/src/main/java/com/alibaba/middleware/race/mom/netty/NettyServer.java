package com.alibaba.middleware.race.mom.netty;

import java.net.InetSocketAddress;

import org.apache.log4j.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.DefaultEventExecutorGroup;

public class NettyServer {
	private static Logger logger = Logger.getLogger(NettyServer.class);
	// netty 参数
	protected final ServerBootstrap serverBootstrap;
	protected final EventLoopGroup eventLoopGroupWorker;
	protected final EventLoopGroup eventLoopGroupBoss;
	protected DefaultEventExecutorGroup defaultEventExecutorGroup;
	protected int port=0;
	public NettyServer()
	{
		this.serverBootstrap = new ServerBootstrap();
		this.eventLoopGroupBoss = new NioEventLoopGroup(1);
		this.eventLoopGroupWorker = new NioEventLoopGroup(3);
		this.defaultEventExecutorGroup = new DefaultEventExecutorGroup(8);
	}
	public void start()
	{
		ServerBootstrap childHandler = //
                this.serverBootstrap.group(this.eventLoopGroupBoss, this.eventLoopGroupWorker)
                    .channel(NioServerSocketChannel.class)
                    //
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //
                    .option(ChannelOption.SO_REUSEADDR, true)
                    //
                    .option(ChannelOption.SO_KEEPALIVE, false)
                    //
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //
                    .localAddress(9999)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast(
                                //
                                defaultEventExecutorGroup, //
                                new NettyEncoder(), //
                                new NettyDecoder(), //
                                new NettyServerHandler());
                        }
                    });
		 try {
	            ChannelFuture sync = this.serverBootstrap.bind().sync();
	            InetSocketAddress addr = (InetSocketAddress) sync.channel().localAddress();
	            this.port = addr.getPort();
	        }
	        catch (InterruptedException e1) {
	            throw new RuntimeException("this.serverBootstrap.bind().sync() InterruptedException", e1);
	        }
	}
	public void processMessageReceived(ChannelHandlerContext ctx, NettyCommand msg)
	{
		logger.debug("NettyServer收到信息");
	}
	class NettyServerHandler extends SimpleChannelInboundHandler<NettyCommand> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, NettyCommand msg) throws Exception {
            processMessageReceived(ctx, msg);
        }
    }
}
