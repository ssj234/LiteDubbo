package com.github.ssj234.log;

import org.apache.log4j.spi.LoggerFactory;

public class Log4jLogger implements Logger {

	private org.apache.log4j.Logger logger;

	public Log4jLogger(Class clazz) {
		logger = org.apache.log4j.Logger.getLogger(clazz);
	}

	public void log(Object message) {
		logger.info(message);
	}

	public void error(Throwable message) {
		logger.error(message);
	}

}
