package com.p2p.processor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.p2p.processor.chain.CMDChain;
import com.p2p.protocol.IMDecoder;
import com.p2p.protocol.IMMessage;

import io.netty.channel.Channel;
import io.netty.util.AttributeKey;

public class MsgProcessor {
	// 记录所有在线的人员
	public static Map<String, Channel> onlineUsers = new ConcurrentHashMap<>();

	private IMDecoder imd = new IMDecoder();
//	private IMEncoder ime = new IMEncoder();

	// 自定义属性
	private final AttributeKey<String> SENDER = AttributeKey.valueOf("sender");
//	private final AttributeKey<String> RECEIVER = AttributeKey.valueOf("receiver");
//	private final AttributeKey<String> ADDR = AttributeKey.valueOf("addr");
	
	public void logout(Channel client){
		if(getSenderName(client) == null){ return; }
		onlineUsers.remove(client.attr(SENDER).get());
	}
	private Object getSenderName(Channel client) {

		return client.attr(SENDER).get();
	}
	public void sendMsg(Channel client,String msg){
		sendMsg(client,imd.decode(msg));
	}
	private void sendMsg(Channel client, IMMessage msg) {
		if (null == msg) {
			return;
		}
		String cmd = msg.getCmd();
		CMDChain chain = ChainMap.getChain(cmd);
		chain.cmdChain(msg, client, onlineUsers);
//		System.out.println(cmd);
		//这里可以写成链式结构
//		if(IMP.REGISTER.getName().equals(cmd)){
//			User u = getUserByMessage(msg);
//			System.out.println("========");
//			try {
//				boolean isSuccess= ParseXML.insertUserAndFriend(u, null);
//				if(!isSuccess){
//					msg.setContent("0");
//					String info = ime.encode(msg);
//					client.writeAndFlush(new TextWebSocketFrame(info));
//					return;
//				}else{
//					msg.setContent("注册成功");
//					String info = ime.encode(msg);
//					client.writeAndFlush(new TextWebSocketFrame(info));
////					System.out.println("============");
//					return;
//				}
//			} catch (Exception de) {
//				de.printStackTrace();
//			}
//		}else if (IMP.LOGIN.getName().equals(cmd)) {
//			User u = getUserByMessage(msg);
//			Element e = null;
//			try {
//				e= ParseXML.getUserElement(u);
//				if(null==e){
//					msg.setContent("0");
//					String info = ime.encode(msg);
//					client.writeAndFlush(new TextWebSocketFrame(info));
//					return;
//				}
//			} catch (DocumentException de) {
//				de.printStackTrace();
//			}
//			//TODO 获得这个人的全部朋友,这部分可以简化,
//			String friends = getFriends(ParseXML.getFriendsName(e));
//			msg.setContent(friends);
//			String info = ime.encode(msg);
//			client.writeAndFlush(new TextWebSocketFrame(info));
//			client.attr(SENDER).getAndSet(msg.getSender());
//			client.attr(ADDR).getAndSet(getIpAddr(client));
//			onlineUsers.put(msg.getSender(), client);
//			System.out.println("总在线人数"+onlineUsers.size());
//			return;
//		}else if(IMP.CHAT.getName().equals(cmd)){
//			msg.setTime(sysTime());//装载当前时间
//			String sender = msg.getSender();
//			String receiver = msg.getReceiver();
//			//获得接收者的客户端
//			Channel cl = onlineUsers.get(receiver);
//			//当前发送的客户端
//			Channel mine = onlineUsers.get(sender);
//			if(cl!=null){
//				msg.setSender(sender);
//				String info = ime.encode(msg);
//				cl.writeAndFlush(new TextWebSocketFrame(info));
//			}else{
//				System.out.println(receiver+"不存在MsgProcessor");
//				msg.setCmd(IMP.SYSTEM.getName());
//				msg.setContent(receiver+"不在线");
//				String info = ime.encode(msg);
//				mine.writeAndFlush(new TextWebSocketFrame(info));
//				return;
//			}
//			if(mine!=null){
//				msg.setSender("you");
//				String info = ime.encode(msg);
//				mine.writeAndFlush(new TextWebSocketFrame(info));
//			}
//		}else if(IMP.ADD.getName().equals(cmd)){
//			User u = new User();
//			u.setId(msg.getSender());
//			User friend = new User();
//			friend.setId(msg.getReceiver());
//			try {
//				boolean addtrue=ParseXML.insertUserAndFriend(u, friend);
//				Channel mine = onlineUsers.get(msg.getSender());
//				if(!addtrue){
//					msg.setContent("0");
//					String info = ime.encode(msg);
//					mine.writeAndFlush(new TextWebSocketFrame(info));
//					return;
//				}
//				Element e= ParseXML.getUserElementById(u);
//				String friends = getFriends(ParseXML.getFriendsName(e));
//				msg.setContent(friends);
//				String info = ime.encode(msg);
//				mine.writeAndFlush(new TextWebSocketFrame(info));
//				
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	}
	
	
//	private User getUserByMessage(IMMessage msg) {
//		User u = new User();
//		u.setId(msg.getSender());
//		u.setPassword(msg.getContent());
//		return u;
//	}
//	private long sysTime() {
//		//获得当前系统时间
//		return System.currentTimeMillis();
//	}
	/**
	 * 用于获取IP地址
	 * @param client
	 * @return
	 */
//	private String getIpAddr(Channel client) {
//
//		return client.remoteAddress().toString().replaceFirst("/", "");
//	}
//	
//	private String getFriends(List<String> list){
//		StringBuilder sb = new StringBuilder();
//		for (String name : list) {
//			sb.append(name).append(",");
//		}
//		return sb.length()==0?"":sb.substring(0, sb.lastIndexOf(",")).toString();
//	}
}
