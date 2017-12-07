package com.github.ssj234.log;

public interface Logger {

	public void log(Object message);
	public void error(Throwable message);
}
