package com.p2p.parseXml;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

import com.p2p.handler.HttpHandler;

public class ParseCfg {
	
	private static int port;
	private static String getFilePath(){
		URL basePath = HttpHandler.class.getProtectionDomain().getCodeSource().getLocation();
		String path = basePath.getPath() + "/configuration.cfg";
		return path;
	}
	private static File loadCfg(String path){
		File f = new File(path);
		isNull(f);
		return f;
	}
	private static void isNull(Object obj){
		if(obj==null){
			throw new RuntimeException("没有这个文件");
		}
	}
	public static int getPort(){
		Scanner in = null;
		try {
			in = new Scanner(loadCfg(getFilePath()));
			String line = in.nextLine();
			String[] arr = line.split("=");
			port = Integer.valueOf(arr[1].trim());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}finally{
			in.close();
		}
		return port;
	}
	public static void main(String[] args) {
		System.out.println(getPort());
	}
}
