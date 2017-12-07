package com.github.ssj234.log;

public abstract class LoggerFactory {
	public static LoggerFactory getInstance() {
		return new Log4jFactory();
	}
	public abstract Logger getLogger(Class clazz);
}
