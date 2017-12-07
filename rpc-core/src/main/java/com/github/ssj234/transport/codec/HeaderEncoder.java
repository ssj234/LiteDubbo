package com.github.ssj234.transport.codec;


import java.util.List;

import com.github.ssj234.serializer.JdkSerializer;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.MessageToMessageEncoder;

public class HeaderEncoder extends MessageToByteEncoder<CMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, CMessage msg,
			ByteBuf  out) throws Exception {

//		out.add(msg);

		if (!(msg instanceof CMessage)) {
			return;
		}
		
		CMessage message=(CMessage) msg;
		CHeader header=message.getHeader();
		
//		ByteBuf buffer=(ByteBuf) message.getData();
		out.writeByte(HeaderDecoder.PACKAGE_TAG);
		out.writeByte(header.getEncode());
		out.writeByte(header.getEncrypt());
		out.writeByte(header.getExtend1());
		out.writeByte(header.getExtend2());
		out.writeBytes(header.getSessionId().getBytes());
		out.writeInt(header.getLength());
		out.writeInt(header.getCommandId());
		out.writeBytes(message.getData());
	
	}


	

}