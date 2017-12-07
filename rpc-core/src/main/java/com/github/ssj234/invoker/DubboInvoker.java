package com.github.ssj234.invoker;

import com.github.ssj234.transport.Client;

public class DubboInvoker implements Invoker{
	Client client;
	String serviceName;
	public DubboInvoker(Client client,String serviceName) {
		this.client = client;
		this.serviceName = serviceName;
	}

	public Class getInterfaceClass() {
		try {
			return Class.forName(serviceName);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Result doInvoker(Invocation invocation) {
		return client.send(invocation);
	} 
}
