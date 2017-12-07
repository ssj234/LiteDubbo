package com.github.ssj234.transport;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

public class Request implements Serializable{
	public static AtomicInteger count = new AtomicInteger(0);
	String id;
	Object data;
	public Request(Object data) {
		this.data = data;
		this.id = genrateId();
	}
	
	private String genrateId() {
		return "req$" + count.incrementAndGet();
	}

	public static AtomicInteger getCount() {
		return count;
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
