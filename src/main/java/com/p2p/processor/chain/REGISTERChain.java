package com.p2p.processor.chain;

import java.util.Map;

import com.p2p.domain.User;
import com.p2p.parseXml.ParseXML;
import com.p2p.protocol.IMMessage;

import io.netty.channel.Channel;

public class REGISTERChain extends CMDChain {
	@Override
	public void cmdChain(IMMessage msg,Channel sender, Map<String, Channel> onlineUsers) {
			//将需要的信息转化为对象
			User u = getUserByMessage(msg);
			try {
				//判断是否注册成功
				boolean register= ParseXML.insertUserAndFriend(u, null);
				if(register){
					msg.setContent("注册成功");  //如果不是CHAT,content返回给前台的是判定信息
				}else{
					msg.setContent("0");
				}
				sendMessage(sender,msg);
			} catch (Exception e) {
				e.printStackTrace();
			}
	}

}
