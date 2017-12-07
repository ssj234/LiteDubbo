package com.github.ssj234.transport;

import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import com.github.ssj234.invoker.Invocation;
import com.github.ssj234.invoker.Result;

import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.util.concurrent.DefaultPromise;

public class NettyClient implements Client {
	Channel channel;
	public static Map<String, DefaultPromise> FUTURES = new ConcurrentHashMap<String, DefaultPromise>();
	NioEventLoopGroup loop = new NioEventLoopGroup();

	public NettyClient(Channel channel) {
		this.channel = channel;
	}

	public Result send(Invocation invocation) {
		Request request = new Request(invocation);
		Result result = new Result();
		channel.writeAndFlush(request);
		final DefaultPromise<Response> promise = new DefaultPromise<Response>(loop.next());
		FUTURES.put(request.getId(), promise);
		try {
			Response response = promise.get(120, TimeUnit.SECONDS);//默认1秒超时，response中的data是result
			return (Result) response.getData();
		} catch (Exception e) {
			result.setException(e);
		}
		return result;
	}

}
