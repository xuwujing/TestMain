package com.test.othersTest;



/**
 * @author ZERO
 * @date 2017-4-24 ����3:53:28
 * @Description  �̳С�static��̬�顢++����
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
		y=x++ + ++x;   //++x�ȶ�x���Լ����㣬��ʹ��x��ֵ��x++����ʹ��x��ֵ���Լ�����
		System.out.println("y:"+y);
	}
			
}
