package com.p2p.protocol;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

public class IMDecoder extends ByteToMessageDecoder {
	//正则解析协议内容
	private Pattern p = Pattern.compile("^\\[(.*)\\](\\s\\-\\s(.*))?");
	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		try{
			final int length = in.readableBytes();
			final byte[] arr = new byte[length];
			in.getBytes(in.readerIndex(),arr,0,length);
			String info = new String(arr,in.readerIndex(),length);
			if(!(null == info || "".equals(info.trim()))){
				//如果拿到的字符串内容不是自定义协议，那么忽略
				if(!IMP.isIMP(info)){
					ctx.channel().pipeline().remove(this);
					return;
				}
			}
			out.add(new MessagePack().read(arr,IMMessage.class));
			in.clear();
		}catch(Exception e){
			//交给其他解码器去解析
			ctx.channel().pipeline().remove(this);
		}
	}
	
	public IMMessage decode(String msg){
		if(null==msg||"".equals(msg)){
			return null;
		}
		Matcher m = p.matcher(msg);
		String header = "";//消息头
		String content ="";//消息体
		if(m.matches()){
			header = m.group(1);
			content = m.group(3);
		}
//		System.out.println(header);//获取消息头
//		System.out.println(content);//获取消息体
		String[] headers = header.split("\\]\\[");
		String cmd = headers[0];
		//获取发送时间
		long time =Long.parseLong(headers[1]);
		String sender = headers[2];
		IMMessage info = new IMMessage();
		info.setCmd(cmd);
		info.setSender(sender);
		info.setTime(time);
		info.setContent(content);//当CMD为LOGIN的时候,content表示密码
		if(IMP.LOGIN.getName().equals(cmd)||IMP.REGISTER.getName().equals(cmd)){
			return info;
		}else if(IMP.CHAT.getName().equals(cmd)||IMP.ADD.getName().equals(cmd)){
			info.setContent(content);
			info.setReceiver(headers[3]);
			return info;
		}
		return null;
	}
	public static void main(String[] args) {
		String info = "[LOGIN][TIME][SENDER][RECEIVER] - i love you";
		IMDecoder im = new IMDecoder();
		im.decode(info);
	}
}
