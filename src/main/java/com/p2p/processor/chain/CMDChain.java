package com.p2p.processor.chain;

import java.util.List;
import java.util.Map;

import com.p2p.domain.User;
import com.p2p.protocol.IMEncoder;
import com.p2p.protocol.IMMessage;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.AttributeKey;

public abstract class CMDChain {
	protected static IMEncoder ime = new IMEncoder();
	protected final AttributeKey<String> SENDER = AttributeKey.valueOf("sender");
	public abstract void cmdChain(IMMessage msg,Channel sender,Map<String, Channel> onlineUsers);
	protected User getUserByMessage(IMMessage msg) {
		User u = new User();
		u.setId(msg.getSender());
		u.setPassword(msg.getContent());
		return u;
	}
	protected String getFriends(List<String> list){
		StringBuilder sb = new StringBuilder();
		for (String name : list) {
			sb.append(name).append(",");
		}
		return sb.length()==0?"":sb.substring(0, sb.lastIndexOf(",")).toString();
	}
	protected void sendMessage(Channel channel,IMMessage msg){
		String info = ime.encode(msg);
		channel.writeAndFlush(new TextWebSocketFrame(info));
	}
	protected long sysTime() {
		//获得当前系统时间
		return System.currentTimeMillis();
	}
}
