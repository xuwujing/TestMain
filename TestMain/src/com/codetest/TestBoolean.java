package com.codetest;
/**
 * @author ZERO
 * @version 2017-4-17 ����6:05:37
 * ��˵�� booleanԴ����
 */
public class TestBoolean {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		 {
		        Boolean bool1 = Boolean.valueOf(true);       //�����ʹ��valueof��������new������Boolean�����ǲ��ϵ��´���һ��ʵ�����󣬶�valueOf���Ƿ���Boolean����ľ�̬��Ա����
		        Boolean bool2 = Boolean.valueOf("True");    //������һ�������֤ʹ��String������Ϊ����ʱ�������ִ�Сд�ġ�
		        Boolean bool3 = Boolean.valueOf("ASD");
		        boolean x1 = bool1.booleanValue();
		        boolean x2 = bool2.booleanValue();
		        System.out.println("bool1:" + x1 + ",bool2:" + x2 + ",bool3:" + bool3);
		        boolean x3 = bool1.equals(bool2);       //���������֤��Ԫģʽ��ʹ�õ���ͬһ������
		        boolean x4 = bool1.equals(bool3);       //�϶�����ͬһ��������
		        System.out.println("bool1.equals(bool2):" + x3 + ",bool1.equals(bool3):" + x4);
		        String str1 = Boolean.toString(bool1);      //�ɼ�Boolean�����ǿ���ת�����ַ���
		        String str2 = Boolean.toString(false);      
		        String str3 = bool3.toString();
		        System.out.println("bool1:" + str1 + ",str2:" + str2 + ",bool3:" + str3);
		        boolean x5 = Boolean.parseBoolean("ASD");         //Դ����ֱ���ж�Ȼ����true�Աȣ���˴�ӡΪfalse
		        System.out.println(x5);
		    }

	}

}
