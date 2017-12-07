package com.github.ssj234.provider;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import com.github.ssj234.invoker.Invoker;
import com.github.ssj234.invoker.ProviderInvoker;
import com.github.ssj234.protocol.Protocol;
import com.github.ssj234.registry.Registry;

public class Provider {

	private Set<Registry> registrys = new LinkedHashSet<Registry>();
	private Set<Protocol> protocols = new LinkedHashSet<Protocol>();
	public static Map<String,Invoker> invokers = new HashMap<String,Invoker>();
	public void export(Class clazz,Object target) {
		//1. 创建Invoker
		Invoker invoker = invokers.get(clazz.getName());
		if(invoker == null) {
			invoker = getInvoker(clazz,target) ;
			invokers.put(clazz.getName(), invoker);
		}
		//2. 发布到协议
		for(Protocol protocol : protocols) {
			this.export(protocol,invoker);
		}
	}
	
	private void export(Protocol protocol,Invoker invoker) {
		Export export = protocol.getExport(invoker.getInterfaceClass().getName());
		if(export != null) {
			return;
		}
		//1. 注册到注册中心
		for(Registry registry : registrys) {
			registry.registry(protocol,invoker);
		}
		//2. 发布到协议中
		protocol.export(invoker);
	}

	public void addRegistry(Registry registry) {
		registrys.add(registry);
	}
	
	public void addProtocol(Protocol protocol) {
		protocols.add(protocol);
	}
	
	private Invoker getInvoker(Class clazz,Object target) { // 创建一个Invoker，收到请求后会调用Invoker的doInvoker方法
		return new ProviderInvoker(clazz,target);
	}
}
