package com.github.ssj234.invoker;

import java.util.List;

public class AllInOneInvoker implements Invoker{
	List<Invoker> list;
	public AllInOneInvoker(List<Invoker> list) {
		this.list = list;
	}

	public Class getInterfaceClass() {
		return list.get(0).getInterfaceClass();
	}

	/**
	 * 这里可以实现router、失败重试等
	 */
	public Result doInvoker(Invocation invocation) {
		return list.get(0).doInvoker(invocation);
	}

}
