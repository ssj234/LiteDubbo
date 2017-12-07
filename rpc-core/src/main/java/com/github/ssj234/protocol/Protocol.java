package com.github.ssj234.protocol;

import java.util.concurrent.ConcurrentHashMap;

import com.github.ssj234.invoker.Invoker;
import com.github.ssj234.provider.Export;
import com.github.ssj234.transport.Server;

public abstract class Protocol extends ProtocolPointEnd{
	
	private ConcurrentHashMap<String, Export> exports = new ConcurrentHashMap<String, Export>();
	private ConcurrentHashMap<String, Invoker> refers = new ConcurrentHashMap<String, Invoker>();
	public Protocol(String ip, int port) {
		super(ip, port);
	}
	
	public abstract void export(Invoker invoker);
	public abstract Invoker refer(String serviceName);
	
	public abstract String getPortocolName();
	
	public void setExport(String serviceName,Export export) {
		exports.put(serviceName, export);
	}
	public Export getExport(String serviceName) {
		return exports.get(serviceName);
	}
	public String getServiceUrl(String serviceName) {
		return String.format("%s://%s:%d/%s", getPortocolName(),getIp(),getPort(),serviceName);
	}
	
	
	public void setRefers(String serviceName,Invoker export) {
		refers.put(serviceName, export);
	}
	public Invoker getRefers(String serviceName) {
		return refers.get(serviceName);
	}
}
