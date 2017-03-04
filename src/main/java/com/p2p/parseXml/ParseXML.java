package com.p2p.parseXml;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.p2p.domain.User;
import com.p2p.handler.HttpHandler;

public class ParseXML {
	private static final String USER = "user";// 用户 标签
	private static final String ATTR_ID = "id"; // 用户帐号/朋友帐号
	private static final String ATTR_PASSWORD = "password";// 用户密码
	private static final String USER_FRIEND = "friend";// 用户好友标签
	// 得到用户信息文件
	public static Document getUsersXml() throws DocumentException {
		SAXReader reader = new SAXReader();
		String path = getXMLPath();
		File f = new File(path);
		if (!f.exists()) {
			System.out.println("xml is not exist");
			return null;
		}
		Document d = reader.read(new File(path));
		return d;
	}
	/**
	 * 用于判断用户是否存在
	 * @param u
	 * @return
	 * @throws DocumentException
	 */
	public static boolean isExistUser(User u) throws DocumentException{
		Document d = getUsersXml();
		Element root = d.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> userlist = root.elements();
		for (Element element : userlist) {
			String id = element.attribute(ATTR_ID).getStringValue();
			if (id.equals(u.getId())) {
				return true;
			}
		}
		return false;
	}
	//用于登录验证
	public static Element getUserElement(User u) throws DocumentException {
		//怕文件修改,每次重新加载
		Document d = getUsersXml();
		Element root = d.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> userlist = root.elements();
		for (Element element : userlist) {
			String id = element.attribute(ATTR_ID).getStringValue();
			String pwd = element.attribute(ATTR_PASSWORD).getStringValue();
			if (id.equals(u.getId()) && pwd.equals(u.getPassword())) {
				return element;
			}
		}
		return null;
	}
	public static Element getUserElementById(User u) throws DocumentException {
		//怕文件修改,每次重新加载
		Document d = getUsersXml();
		Element root = d.getRootElement();
		@SuppressWarnings("unchecked")
		List<Element> userlist = root.elements();
		for (Element element : userlist) {
			String id = element.attribute(ATTR_ID).getStringValue();
			if (id.equals(u.getId())) {
				return element;
			}
		}
		return null;
	}
	//用于获得这个人的所有朋友
	public static List<String> getFriendsName(Element user) {
		@SuppressWarnings("unchecked")
		List<Element> friendlist = user.elements();
		List<String> allname = new ArrayList<>();
		for (Element element : friendlist) {
			allname.add(element.attributeValue(ATTR_ID));
		}
		return allname;

	}
	//用于判断是不是已经是朋友关系
	private static boolean isFriend(List<String> names,User u){
		for (String id : names) {
			if(id.equals(u.getId())){
				return true;
			}
		}
		return false;
	}
//	public static synchronized boolean insertFriend(User u) throws Exception{
//		Element friend =DocumentHelper.createElement(USER_FRIEND);
//		friend.addAttribute(ATTR_ID, u.getId());
//		Document d = getUsersXml();
//		Element root = d.getRootElement();
//		List<Element> userlist = root.elements();
//		for (Element user : userlist) {
//			String id = user.attribute(ATTR_ID).getStringValue();
//			if (id.equals(u.getId())) {
//				if(isFriend(getFriendsName(user),u))
//				{
//					return false; //表示已经是好友
//				}
//				user.add(friend);
//			}
//		}
//		FileOutputStream fos = new FileOutputStream(getXMLPath());
//		OutputFormat format = OutputFormat.createPrettyPrint();  
//		format.setEncoding("utf-8"); 
//		XMLWriter xw = new XMLWriter(fos,format);
//		xw.write(d);
//		xw.flush();
//		xw.close();
//		return true;
//		
//	}
	//friend不为空的时候表示添加好友,用于注册,嗯不支持删除,懒得写了
	public static synchronized boolean insertUserAndFriend(User u,User friend) throws Exception {
		OutputFormat format = OutputFormat.createPrettyPrint();  
		format.setEncoding("utf-8"); 
		Document d = getUsersXml();
		Element root = d.getRootElement();
		if(isExistUser(u)){ //表示已经存在这个人了
			if(friend!=null&&isExistUser(friend)){
				Element efriend =DocumentHelper.createElement(USER_FRIEND);
				efriend.addAttribute(ATTR_ID, friend.getId());
				@SuppressWarnings("unchecked")
				List<Element> userlist = root.elements();
				for (Element user : userlist) {
					String id = user.attribute(ATTR_ID).getStringValue();
					if (id.equals(u.getId())) {
						if(isFriend(getFriendsName(user),friend))
						{
							return false; //表示已经是好友
						}
						user.add(efriend);
						FileOutputStream fos = new FileOutputStream(getXMLPath());
						XMLWriter xw = new XMLWriter(fos,format);
						xw.write(d);
						xw.flush();
						xw.close();
						return true;
					}
				}
			}
			return false;
		}else{
			Element e =DocumentHelper.createElement(USER);
			e.addAttribute(ATTR_ID, u.getId());
			e.addAttribute(ATTR_PASSWORD, u.getPassword());
			root.add(e);
			FileOutputStream fos = new FileOutputStream(getXMLPath());
			XMLWriter xw = new XMLWriter(fos,format);
			xw.write(d);
			xw.flush();
			xw.close();
			return true;
		}
	}
	public static void main(String[] args) throws Exception {
		User u = new User();
		u.setId("47");
		User f = new User();
		f.setId("47oo");
		System.out.println(insertUserAndFriend(u, f));
	}
	//得到table路径,就相当于数据库表
	private static String getXMLPath(){
		URL basePath = HttpHandler.class.getProtectionDomain().getCodeSource().getLocation();
		String path = basePath.getPath() + "/users.xml";
		return path;
	}
}
