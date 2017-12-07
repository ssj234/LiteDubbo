package com.github.ssj234.serializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.github.ssj234.transport.Request;
import com.github.ssj234.transport.Response;

public class JdkSerializer implements Serializer {

	public Request unSerializeRequest(byte[] bytes) {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(is);
			return (Request) ois.readObject(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;  
	}

	public Response unSerializeResponse(byte[] bytes) {
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		ObjectInputStream ois;
		try {
			ois = new ObjectInputStream(is);
			return (Response) ois.readObject(); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;  
	}

	public byte[] serialize(Request request) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			
			ObjectOutputStream oos = new ObjectOutputStream(out);  
			oos.writeObject(request);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return out.toByteArray();
	}

	public byte[] serialize(Response response) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			
			ObjectOutputStream oos = new ObjectOutputStream(out);  
			oos.writeObject(response);
		} catch (IOException e) {
			e.printStackTrace();
		}  
		return out.toByteArray();
	}

	public static void main(String[] args) {
		Serializer serializer = new JdkSerializer();
		Request request = new Request(new NullPointerException("xxxx"));
		byte [] bytes = serializer.serialize(request);
		
		Request requestUS = serializer.unSerializeRequest(bytes);
		System.out.println(requestUS.getData());
	}
}
