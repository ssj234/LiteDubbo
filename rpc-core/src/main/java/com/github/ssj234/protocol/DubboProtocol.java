package com.github.ssj234.protocol;

import java.util.concurrent.ConcurrentHashMap;

import com.github.ssj234.invoker.DubboInvoker;
import com.github.ssj234.invoker.Invoker;
import com.github.ssj234.provider.DubboExpoter;
import com.github.ssj234.provider.Export;
import com.github.ssj234.transport.Client;
import com.github.ssj234.transport.NettyTransport;
import com.github.ssj234.transport.Server;

public class DubboProtocol extends Protocol {
	private  static ConcurrentHashMap<String, Server> servers = new ConcurrentHashMap<String, Server>();
	private  static ConcurrentHashMap<String, Client> clients = new ConcurrentHashMap<String, Client>();
	public static Server getServer(String key) {
		return servers.get(key);
	}
	
	public static void setServer(String key,Server server) {
		servers.put(key,server);
	}
	public static Client getClient(String key) {
		return clients.get(key);
	}
	
	public static void setClient(String key,Client client) {
		clients.put(key,client);
	}
	public DubboProtocol(String ip, int port) {
		super(ip, port);
	}

	@Override
	public void export(Invoker invoker) {
		Export export = new DubboExpoter(invoker);
		super.setExport(invoker.getInterfaceClass().getName(), export);
		openServer();
	}

	private void openServer() {
		// 根据ip和port查看是否有已经打开的服务器
		String serverKey = getIp()+getPort();
		Server server = DubboProtocol.getServer(serverKey);
		if(server != null) {
			return;
		}
		server = new NettyTransport().bind(getIp(), getPort());
		DubboProtocol.setServer(serverKey, server);
	}

	@Override
	public String getPortocolName() {
		return "dubbo";
	}

	@Override
	public Invoker refer(String serviceName) {
		Invoker invoker = super.getRefers(serviceName);
		if(invoker != null) {
			return invoker;
		}
		Client client = openClient();
		invoker = new DubboInvoker(client,serviceName);
		super.setRefers(serviceName, invoker);
		return invoker;
	}

	private Client openClient() {
		String clientKey = getIp()+getPort();
		Client client = DubboProtocol.getClient(clientKey);
		if(client != null) {
			return client;
		}
		client = new NettyTransport().connect(getIp(), getPort());
		DubboProtocol.setClient(clientKey, client);
		return client;
	}
	
}
