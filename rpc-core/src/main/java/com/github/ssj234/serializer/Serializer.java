package com.github.ssj234.serializer;

import com.github.ssj234.transport.Request;
import com.github.ssj234.transport.Response;

public interface Serializer {

	public Request unSerializeRequest(byte bytes[]);
	public Response unSerializeResponse(byte bytes[]);
	
	public byte[] serialize(Request request);
	public byte[] serialize(Response response);
}
