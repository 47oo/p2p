package com.p2p.processor.chain;

import java.util.Map;

import org.dom4j.Element;

import com.p2p.domain.User;
import com.p2p.parseXml.ParseXML;
import com.p2p.protocol.IMMessage;

import io.netty.channel.Channel;

public class ADDChain extends CMDChain {

	@Override
	public void cmdChain(IMMessage msg, Channel sender, Map<String, Channel> onlineUsers) {
		User u = loadUser(msg.getSender());
		User friend = loadUser(msg.getReceiver());
		try {
			boolean add = ParseXML.insertUserAndFriend(u, friend);
			if (!add) {
				msg.setContent("0");
				sendMessage(sender, msg);
				return;
			}
			Element e = ParseXML.getUserElementById(u);
			String friends = getFriends(ParseXML.getFriendsName(e));
			msg.setContent(friends);
			sendMessage(sender, msg);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private User loadUser(String id) {
		User u = new User();
		u.setId(id);
		return u;
	}
}
