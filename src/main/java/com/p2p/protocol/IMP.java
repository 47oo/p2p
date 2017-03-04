package com.p2p.protocol;

public enum IMP {
	/** 系统消息 */
	SYSTEM("SYSTEM"),
	/** 登录指令 */
	LOGIN("LOGIN"),
	/** 登出指令 */
	LOGOUT("LOGOUT"),
	/** 聊天 */
	CHAT("CHAT"),
	/**注册 */
	REGISTER("REGISTER"),
	/**添加好友*/
	ADD("ADD");
	private String name;
	IMP(String name){
		this.name =name;
	}
	/**
	 * 判断是不是协议支持的命令，如果是就解析，如果不是，就原文输出
	 * @param msg
	 * @return
	 */
	public static boolean isIMP(String msg){
		return msg.matches("^\\[(SYSTEM|LOGIN|LOGOUT|CHAT|REGISTER|ADD)\\]");
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
