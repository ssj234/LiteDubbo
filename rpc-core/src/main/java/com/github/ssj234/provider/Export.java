package com.github.ssj234.provider;

import com.github.ssj234.invoker.Invoker;

public abstract class Export {
	private Invoker invoker;

	public Export(Invoker invoker) {
		this.invoker = invoker;
	}

	public Invoker getInvoker() {
		return this.invoker;
	}
}
