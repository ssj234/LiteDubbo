package com.github.ssj234.transport.codec;
public class CHeader {

	private byte encode;
	private byte encrypt;
	private byte extend1;
	private byte extend2;
	private String sessionId;//32位
	private int length;
	private int commandId;
	
	public CHeader clone(){
		try {
			return (CHeader) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public CHeader(){
		
	}
	
	public CHeader(String sessionId){
		this.encode=0;
		this.encrypt=0;
		this.sessionId=sessionId;
	}
	
	public CHeader(byte encode,byte encrypt,
			byte extend1,byte extend2,
			String sessionId,int length,int commandId ){
		this.encode = encode;
		this.encrypt = encrypt;
		this.extend1 = extend1;
		this.extend2 = extend2;
		this.sessionId = sessionId;
		this.length = length;
		this.commandId = commandId;
	}
	
	@Override
	public String toString() {
		return "header [encode=" + encode + ",encrypt=" + encrypt + ",extend1="
				+ extend1 + ",extend2=" + extend2 + ",sessionId=" + sessionId
				+ ",length=" + length + ",commandId=" + commandId + "]";
	}

	public byte getEncode() {
		return encode;
	}

	public void setEncode(byte encode) {
		this.encode = encode;
	}

	public byte getEncrypt() {
		return encrypt;
	}

	public void setEncrypt(byte encrypt) {
		this.encrypt = encrypt;
	}

	public byte getExtend1() {
		return extend1;
	}

	public void setExtend1(byte extend1) {
		this.extend1 = extend1;
	}

	public byte getExtend2() {
		return extend2;
	}

	public void setExtend2(byte extend2) {
		this.extend2 = extend2;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	public int getCommandId() {
		return commandId;
	}

	public void setCommandId(int commandId) {
		this.commandId = commandId;
	}
	
	
	
	
}