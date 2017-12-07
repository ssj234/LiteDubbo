package com.github.ssj234.consumer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.github.ssj234.invoker.AllInOneInvoker;
import com.github.ssj234.invoker.Invoker;
import com.github.ssj234.registry.Registry;

public class Consumer {
	private Set<Registry> registrys = new LinkedHashSet<Registry>();
	private Map<Class,Object> refers = new HashMap<Class,Object>();
	/**
	 * 根据clazz在注册中心查找服务，并生成Invoker，包装为clazz的实现类返回
	 * @param clazz
	 * @return
	 */
	public Object refer(Class clazz) {
		Object ret = refers.get(clazz);
		if(ret != null) {
			return ret;
		}
		//1. 在注册中心查找
		List<Invoker> invokers = new ArrayList<Invoker>();
		for(Registry registry : registrys) {
			registry.subscribe(clazz.getName(),invokers);
		}
		// 2. 合成一个invoker
		Invoker allInOne = new AllInOneInvoker(invokers);
		// 3. 生成一个代理类
		Object object = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[] {clazz}, new ConsumerInvokeHandler(allInOne));
		refers.put(clazz, object);
		return object;
	}
	
	public void addRegistry(Registry registry) {
		registrys.add(registry);
	}
}
