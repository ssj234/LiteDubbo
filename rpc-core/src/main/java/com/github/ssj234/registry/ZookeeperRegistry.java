package com.github.ssj234.registry;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.github.ssj234.invoker.Invoker;
import com.github.ssj234.log.Logger;
import com.github.ssj234.log.LoggerFactory;
import com.github.ssj234.protocol.DubboProtocol;
import com.github.ssj234.protocol.Protocol;

public class ZookeeperRegistry implements Registry {
	Logger logger = LoggerFactory.getInstance().getLogger(ZookeeperRegistry.class);
	private String ip;
	private int port;
	private static CuratorFramework client;

	public ZookeeperRegistry(String ip, int port) {
		this.ip = ip;
		this.port = port;
		init();
	}

	public void init() {
		CuratorFrameworkFactory.Builder builder = CuratorFrameworkFactory.builder()
                .connectString(ip+":"+port)
                .retryPolicy(new RetryNTimes(1, 1000))
                .connectionTimeoutMs(5000);
        client = builder.build();
        client.start();
	}

	public void registry(Protocol protocol,Invoker invoker) {
		String serviceName = invoker.getInterfaceClass().getName();
		String url = protocol.getServiceUrl(serviceName);
		// 
		try {
			client.create().withMode(CreateMode.EPHEMERAL).forPath(getPath(url,serviceName), "".getBytes()); 
		} catch (Exception e) {
			logger.error(e);
//			throw new IllegalAccessError(e.getMessage());
		}
	}
	// node:/dubbox/com.github.ssj234.service.ISearchPrice/providers/dubbo%3A%2F%2F127.0.0.1%3A20881%2Fcom.github.ssj234.service.ISearchPrice
	private String getPath(String url,String serviceName) {
		return getPathProvider(serviceName) + "/" + URLEncoder.encode(url);
	}
	
	private String getPathProvider(String serviceName) {
		return "/dubbox/" + serviceName + "/providers";
	}

	public void subscribe(final String serviceName, final List<Invoker> invokers) {
		// 1. 监听provider节点的变化，更新invokers列表,更新操作就不实现了
		try {
			client.getChildren().usingWatcher(new Watcher() {
				public void process(WatchedEvent event) {
					update(serviceName, invokers);
				}}).forPath(getPathProvider(serviceName));
		} catch (Exception e) {
			logger.error(e);
		}
		update(serviceName, invokers);
	}
	
	public void update(String serviceName, List<Invoker> invokers) {
		try {
			List<String> children = client.getChildren().forPath(getPathProvider(serviceName));
			for(String provider : children) {
				Invoker invoker =  getInvoker(provider);
				if(invoker == null) {
					logger.log("invoker is null!");
				}else {
					invokers.add(invoker);
				}
			}
		} catch (Exception e) {
			logger.error(e);
		}
	}
	
	private Invoker getInvoker(String provider) {
		// 1. 根据provider选择protocol，现在都是DubboProtocol
		Invoker invoker;
		String url = URLDecoder.decode(provider); 
		String pattern = "dubbo://([\\w\\.]*+):(\\w+)/([\\w\\.]+)";
		Pattern r = Pattern.compile(pattern);
		Matcher m = r.matcher(url);
		if(m.find()) {
			String protocolIp = m.group(1);
			String protocolPort = m.group(2);
			String serviceName = m.group(3);
			Protocol protocol = new DubboProtocol(protocolIp, Integer.valueOf(protocolPort));
			invoker = protocol.refer(serviceName);
		}else {
			logger.log("can not decode protocol");
			return null;
		}
		return invoker;
	}
	
//	public static void main(String[] args) {
//		String pattern = "dubbo://([\\w\\.]*+):(\\w+)/([\\w\\.]+)";
//		Pattern r = Pattern.compile(pattern);
//		Matcher m = r.matcher("dubbo://127.0.0.1:20881/com.github.ssj234.service.ISearchPrice");
//		if(m.find()) {
//			System.out.println(m.group(0));
//			System.out.println(m.group(1));
//			System.out.println(m.group(2));
//			System.out.println(m.group(3));
//		}
//	}
}
