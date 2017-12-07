package com.github.ssj234.transport;

import io.netty.channel.EventLoopGroup;

public class NettyServer implements Server {

	private EventLoopGroup bossGroup;
	private EventLoopGroup workGroup;
	public NettyServer(EventLoopGroup bossGroup, EventLoopGroup workGroup) {
		super();
		this.bossGroup = bossGroup;
		this.workGroup = workGroup;
	}
	
	public void close(){
		this.bossGroup.shutdownGracefully();
		this.workGroup.shutdownGracefully();
	}
}
