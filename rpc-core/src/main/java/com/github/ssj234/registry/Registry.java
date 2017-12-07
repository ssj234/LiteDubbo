package com.github.ssj234.registry;

import java.util.List;

import com.github.ssj234.invoker.Invoker;
import com.github.ssj234.protocol.Protocol;

public interface Registry {
	public void registry(Protocol protocol,Invoker invoker);
	public void subscribe(String serviceName,List<Invoker> invokers);
}
