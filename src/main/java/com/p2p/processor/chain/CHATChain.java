package com.p2p.processor.chain;

import java.util.Map;

import com.p2p.protocol.IMMessage;
import com.p2p.protocol.IMP;
import io.netty.channel.Channel;
public class CHATChain extends CMDChain {

	@Override
	public void cmdChain(IMMessage msg, Channel sender, Map<String, Channel> onlineUsers) {
		msg.setTime(sysTime());// 装载当前时间
		// 获得接收者的客户端
		Channel receiver = onlineUsers.get(msg.getReceiver());
		if (receiver != null) {
			msg.setSender(msg.getSender());
			sendMessage(receiver, msg);
		} else {
			msg.setCmd(IMP.SYSTEM.getName());
			msg.setContent(msg.getReceiver() + "不在线");
			sendMessage(sender, msg);
			return;
		}
		msg.setSender("you");
		sendMessage(sender, msg);
	}

}
