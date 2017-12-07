package com.github.ssj234.transport;

import java.util.concurrent.Callable;

import com.github.ssj234.invoker.Invocation;
import com.github.ssj234.invoker.Invoker;
import com.github.ssj234.log.Logger;
import com.github.ssj234.log.LoggerFactory;
import com.github.ssj234.provider.Provider;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {
	Logger logger = LoggerFactory.getInstance().getLogger(NettyServerHandler.class);
	NioEventLoopGroup loop = new NioEventLoopGroup();

	@Override
	public void channelRead(final ChannelHandlerContext ctx, Object msg) throws Exception {
		final Request request = (Request) msg;

		Object data = request.getData();
		if (data instanceof Invocation) {
			final Invocation invocation = (Invocation) data;
			loop.submit(new Runnable() {

				public void run() {
					// 根据
					Response reponse = new Response(request.getId());
					String serviceName = invocation.getInterfaceClass().getName();
					Invoker invoker = Provider.invokers.get(serviceName);
					if (invoker == null) {
						logger.log("can not find invoker of " + serviceName);
						reponse.setData(new NullPointerException("can not find invoker of " + serviceName));
					} else {
						try {
							reponse.setData(invoker.doInvoker(invocation));
						} catch (Throwable e) {
							reponse.setData(e);
						}
					}
					ctx.writeAndFlush(reponse); // 发送出去
				}
			});
		}
	}

}
