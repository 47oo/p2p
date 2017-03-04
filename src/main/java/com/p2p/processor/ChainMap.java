package com.p2p.processor;

import java.util.HashMap;
import java.util.Map;

import com.p2p.processor.chain.ADDChain;
import com.p2p.processor.chain.CHATChain;
import com.p2p.processor.chain.CMDChain;
import com.p2p.processor.chain.LOGINChain;
import com.p2p.processor.chain.REGISTERChain;
import com.p2p.protocol.IMP;

public class ChainMap {
	private static class CMDMap{
		private static Map<String,CMDChain> map = new HashMap<>();
		static{
			map.put(IMP.REGISTER.getName(), new REGISTERChain());
			map.put(IMP.LOGIN.getName(), new LOGINChain());
			map.put(IMP.CHAT.getName(), new CHATChain());
			map.put(IMP.ADD.getName(), new ADDChain());
		}
	}
	public static CMDChain getChain(String cmd){
		return CMDMap.map.get(cmd);
	}
}	
