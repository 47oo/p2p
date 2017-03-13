package com.cqupt.P2P;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

public class Test {
	
	public String love(String name,@Param("paramvalue") Integer value){
		
		return name+value;
	}
	public static void main(String[] args) throws Exception {
		Test t = new Test();
		Class<?> clazz = t.getClass();
		Method[] ms = clazz.getDeclaredMethods();
		System.out.println(ms.length);
		Method m = null;
		for(int i=0;i<ms.length;i++){
			m = ms[i];
			if(m.getName().equals("love")){
				break;
			}
		}
		Parameter[] ps = m.getParameters();
		for(int i=0;i<ps.length;i++){
			Parameter p = ps[i];
			if(p.getAnnotation(Param.class) != null){
				Param an = p.getAnnotation(Param.class);
				System.out.print(an.value()+" ");
				
			}
			System.out.println(p.getType()+" "+p.getName());
		}
		Object obj = m.invoke(t, "123",4);
	}
	
}
