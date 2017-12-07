package com.github.ssj234.consumer;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import com.github.ssj234.invoker.Invocation;
import com.github.ssj234.invoker.Invoker;
import com.github.ssj234.invoker.Result;

public class ConsumerInvokeHandler implements InvocationHandler {
	Invoker invoker;

	public ConsumerInvokeHandler(Invoker invoker) {
		this.invoker = invoker;
	}

	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Result result = invoker.doInvoker(new Invocation(invoker.getInterfaceClass(),method.getName(), method.getParameterTypes(), args));
		
//		com.github.ssj234.invoker.Result cannot be cast to java.lang.String
		return result.getValue();
	}

}
