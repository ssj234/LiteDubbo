package com.github.ssj234.transport;

import com.github.ssj234.log.Logger;
import com.github.ssj234.log.LoggerFactory;
import com.github.ssj234.transport.codec.CMessageChannelHandler;
import com.github.ssj234.transport.codec.HeaderDecoder;
import com.github.ssj234.transport.codec.HeaderEncoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LoggingHandler;

public class NettyTransport implements Transport {
	Logger logger = LoggerFactory.getInstance().getLogger(NettyTransport.class);
	public Server bind(String ip, int port) {
		EventLoopGroup bossGroup=new NioEventLoopGroup();
		EventLoopGroup workGroup=new NioEventLoopGroup();
		try{
			//辅助启动类
			ServerBootstrap b=new ServerBootstrap();
			b.group(bossGroup,workGroup)
				.channel(NioServerSocketChannel.class)//创建的channel为NioServerSocketChannel【nio-ServerSocketChannel】
				.option(ChannelOption.SO_BACKLOG, 1024)
				.childOption(ChannelOption.SO_KEEPALIVE, true) //配置accepted的channel
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast(new LoggingHandler())
						.addLast("decode",new HeaderDecoder())
						.addLast("encode",new HeaderEncoder())
						.addLast("cmessage",new CMessageChannelHandler())
						.addLast(new NettyServerHandler());
					}
				});//处理IO事件的处理类，处理网络事件
			ChannelFuture f=b.bind(ip,port).sync();//绑定端口后同步等待
			logger.log(String.format("bind to [%s:%d]",ip,port));
//			f.channel().closeFuture().sync();//阻塞
		}catch(Exception e){
			e.printStackTrace();
		}finally{
//			bossGroup.shutdownGracefully();
//			workGroup.shutdownGracefully();
		}
		return new NettyServer(bossGroup,workGroup);
	}

	public Client connect(String ip, int port) {
		EventLoopGroup group=new NioEventLoopGroup();
		Channel channel = null;
		try{
			Bootstrap b=new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>(){
					@Override
					protected void initChannel(SocketChannel ch) throws Exception {
						ch.pipeline().addLast("decode",new HeaderDecoder());
						ch.pipeline().addLast("encode",new HeaderEncoder());
						ch.pipeline().addLast("cmessage",new CMessageChannelHandler());
						ch.pipeline().addLast("netty",new NettyClientHandler());
					}
					
				});
			logger.log(String.format("connect to [%s:%d]" , ip,port)); 
			ChannelFuture f = b.connect(ip,port).sync();
//			f.channel().closeFuture().sync();
			channel = f.channel();
		}catch(Exception e){
			e.printStackTrace();
		}finally{
//			group.shutdownGracefully();
		}
		return new NettyClient(channel);
	}

}
