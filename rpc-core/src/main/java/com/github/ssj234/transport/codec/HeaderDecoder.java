package com.github.ssj234.transport.codec;

import java.nio.charset.Charset;
import java.util.List;

import com.github.ssj234.transport.Request;
import com.github.ssj234.transport.Response;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.CorruptedFrameException;
import io.netty.handler.codec.FixedLengthFrameDecoder;

/**
 * https://my.oschina.net/OutOfMemory/blog/290180
 * http://www.jianshu.com/p/ba21eb32ae97
 * 
 * @author shisj
 *<pre>
 *
 *
 *</pre>
 */
public class HeaderDecoder extends ByteToMessageDecoder {

	public static final int HEAD_LENGTH = 45;
	public static final byte PACKAGE_TAG = 0X01;

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in,
			List<Object> out) throws Exception {
		if (in.readableBytes() < HEAD_LENGTH) {//可读的字节数this.writerIndex - this.readerIndex比header还小
			return;
		}

		in.markReaderIndex();//保存readerIndex，调用resetReaderIndex()可以将marked的index回复为readerIndex 
		byte tag = in.readByte();// 读一个字节
		if (tag != PACKAGE_TAG) {//若第一个自己不是0X01开头，抛出异常。
			throw new CorruptedFrameException("非法协议包");
		}
		byte encode = in.readByte();//第一个字节
		byte encrypt = in.readByte();//第一个字节
		byte extend1 = in.readByte();//第一个字节
		byte extend2 = in.readByte();//第一个字节
		byte sessionByte[] = new byte[32];//session

		in.readBytes(sessionByte);//读取到sessionByte

		String sessionId = new String(sessionByte);//session

		int length = in.readInt();//header的leangth，指定body的长度
		int commandId = in.readInt();//命令

		if (in.readableBytes() < length) {//若剩余可读的字节数小于body的长度
			in.resetReaderIndex();//恢复index位置
			return;
		}
		//创建header
		CHeader header = new CHeader(encode, encrypt, extend1, extend2,
				sessionId, length, commandId);
		//创建message
		ByteBuf buf=in.readBytes(length);
		
		CMessage message = new CMessage(header, buf.array());
		//添加到输出
		out.add(message);

	}
	
	private Object unserializer(CMessage message) {
		byte type = message.getHeader().getExtend1();
		if(type == Byte.valueOf((byte) 0)) { // request
			return covert2Request(message.getData());
		}else {
			return covert2Response(message.getData());
		}
	}

	private Response covert2Response(byte[] bytes) {
		return null;
	}

	private Request covert2Request(byte[] bytes) {
		return null;
	}

}