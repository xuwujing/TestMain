package com.test.othersTest;



/**
 * @author ZERO
 * @date 2017-4-24 下午3:53:28
 * @Description  继承、static静态块、++测试
 */
public class exTest extends Exception{
	static{
		int x=5;
	}
    static int x,y;
	/**
	 * @param args
	 * @throws exTest 
	 */
	public static void main(String[] args) throws exTest {
		int i=1,l=2;
//		System.out.println(++l);
		int q=i++; //1
		int w=++l; //3
		System.out.println(q + w);//4
		x--;
		my();
		System.out.println(x+ y++ +x); //2
	}
   
	public static void my(){
		y=x++ + ++x;   //++x先对x做自加运算，在使用x的值。x++是先使用x的值再自加运算
		System.out.println("y:"+y);
	}
			
}
