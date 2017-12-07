package com.github.ssj234.log;

public class Log4jFactory extends LoggerFactory {

	@Override
	public Logger getLogger(Class clazz) {
		return new Log4jLogger(clazz);
	}

}
