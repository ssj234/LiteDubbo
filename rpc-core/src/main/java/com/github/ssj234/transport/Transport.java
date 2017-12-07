package com.github.ssj234.transport;

public interface Transport {
	public Server bind(String ip,int port);
	public Client connect(String ip,int port);
}
