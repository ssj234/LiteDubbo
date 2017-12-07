package com.github.ssj234.invoker;

public interface Invoker {

	public Class getInterfaceClass();
	public Result doInvoker(Invocation invocation);
}
