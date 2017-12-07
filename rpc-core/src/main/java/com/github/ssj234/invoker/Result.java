package com.github.ssj234.invoker;

import java.io.Serializable;

public class Result implements Serializable {

	private Object value;
	private Exception exception;
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public Exception getException() {
		return exception;
	}
	public void setException(Exception exception) {
		this.exception = exception;
	}
	
	
}
