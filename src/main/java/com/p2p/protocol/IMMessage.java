package com.p2p.protocol;

import org.msgpack.annotation.Message;
/**
 * 按照规定传输协议可以包装成的对象
 * @author 47
 *
 */
@Message
public class IMMessage {
	
	private String addr; //IP地址及端口
	private String cmd; //命令没醒,如:LOGIN等
	private long time; //命令发送时间
	private String sender;//命令发送的人
	private String receiver;//命令接收人
	private String content; //消息内容
	public IMMessage(){}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getCmd() {
		return cmd;
	}
	public void setCmd(String cmd) {
		this.cmd = cmd;
	}
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
}
