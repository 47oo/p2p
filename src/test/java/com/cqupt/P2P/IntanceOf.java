package com.cqupt.P2P;

public class IntanceOf {
	// intanceof 对比的是真实对象,而不是引用类型	
	public static void main(String[] args) {
		A a =null;
		B b = null;
		C c = null;
		System.out.println(a instanceof A); //false
		System.out.println(b instanceof B);	//false
		System.out.println(c instanceof C); //false
		System.out.println("==================");
		a = new B();
		System.out.println(a instanceof A); //true
		System.out.println(a instanceof B); //true
		System.out.println(a instanceof C); //false
		System.out.println("==================");
		c = new C();
		System.out.println(c instanceof A); //true
		System.out.println(c instanceof B); //true
		System.out.println(c instanceof C); //true
		System.out.println("===========");
		b = new C();
		System.out.println(b instanceof A); //true
		System.out.println(b instanceof A); //true
		System.out.println(b instanceof A); //true
		System.out.println("===========");
		System.out.println(b.getClass());
		System.out.println("++++++++++++++++++");
		Class ca = null;
		Class cb  = null;
		Class cc = null;
		System.out.println(ca instanceof Class);//false
		System.out.println(cb instanceof Class);//false
		System.out.println(cc instanceof Class);//false
		System.out.println("================");
		ca = A.class;
		cb = B.class;
		cc = C.class;
		System.out.println(ca instanceof Class);//true
		System.out.println(cb instanceof Class);//true
		System.out.println(cc instanceof Class);//true
	}

}
interface A{
	
}
class B implements A{
	
}
class C extends B{
	
}
