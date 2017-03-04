package com.p2p.processor.chain;

import java.util.Map;

import org.dom4j.DocumentException;
import org.dom4j.Element;

import com.p2p.domain.User;
import com.p2p.parseXml.ParseXML;
import com.p2p.protocol.IMMessage;

import io.netty.channel.Channel;

public class LOGINChain extends CMDChain {
	@Override
	public void cmdChain(IMMessage msg,Channel sender, Map<String, Channel> onlineUsers) {
		User u = getUserByMessage(msg);
		// user节点
		Element e = null;
		try {
			// 判断有没有这个人
			e = ParseXML.getUserElement(u);
			if (null == e) {
				msg.setContent("0");
			} else {
				// 如果存在拿到这个人的所有朋友,并返回给前台
				String friends = getFriends(ParseXML.getFriendsName(e));
				msg.setContent(friends);
			}
			sendMessage(sender,msg);
			//将这个客户端添加到所有人中(在线的所有人)
			sender.attr(SENDER).getAndSet(msg.getSender());
			onlineUsers.put(msg.getSender(), sender);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
	}

}
