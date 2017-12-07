package com.github.ssj234.transport.codec;
public class CMessage {

	private CHeader header;
	private byte[] data;
	
	public  CMessage(){
		
	}
	
	public  CMessage(CHeader header){
		this.header=header;
	}
	
	public CMessage(CHeader header, byte[] data) {
		this.header = header;
		this.data = data;
	}

	public CHeader getHeader() {
		return header;
	}

	public void setHeader(CHeader header) {
		this.header = header;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
	
	
}