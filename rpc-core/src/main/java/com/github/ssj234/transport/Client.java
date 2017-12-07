package com.github.ssj234.transport;

import com.github.ssj234.invoker.Invocation;
import com.github.ssj234.invoker.Result;

public interface Client {

	Result send(Invocation invocation);
}
