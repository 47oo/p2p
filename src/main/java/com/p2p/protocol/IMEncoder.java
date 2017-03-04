package com.p2p.protocol;

import org.msgpack.MessagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * IM协议编码其,服务端--->>>客户端需要编码
 * 
 * @author hp
 *
 */
public class IMEncoder extends MessageToByteEncoder<IMMessage> {

	@Override
	protected void encode(ChannelHandlerContext ctx, IMMessage msg, ByteBuf out) throws Exception {
		out.writeBytes(new MessagePack().write(msg));
	}
	/**
	 * 把IMMeaage对象解析称为自定义字符串协议类型
	 * @param msg
	 * @return
	 */
	public String encode(IMMessage msg) {
		if (null == msg) {
			return "";
		}
		String cmd = msg.getCmd();
		String prex = "["+cmd+"]["+msg.getTime()+"]";
		
		if(IMP.LOGIN.getName().equals(cmd)
				||IMP.SYSTEM.getName().equals(cmd)
				||IMP.LOGOUT.getName().equals(cmd)
				||IMP.REGISTER.getName().equals(cmd)){
			prex += ("[" + msg.getSender() + "]");
		}else if(IMP.CHAT.getName().equals(cmd)||IMP.ADD.getName().equals(cmd)){
			prex += ("[" + msg.getSender() + "]");
			prex += ("[" + msg.getReceiver() + "]");
		}
		if(!(null==msg.getContent()||"".equals(msg.getContent()))){
			prex += (" - " + msg.getContent());
		}
		return prex;
	}

}
