package com.github.ssj234.transport;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Response implements Serializable {

	String id;
	Object data;
	public Response(String id) {
		this.id = id;
	}
	
	public Response(String id,Object data) {
		this.data = data;
		this.id = id;
	}

	public static void setCount(AtomicInteger count) {
		Request.count = count;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	

}
