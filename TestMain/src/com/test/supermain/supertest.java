package com.test.supermain;

import com.test.supermain.supertest1.Father;
import com.test.supermain.supertest1.Son;

/**
 * @author ZERO
 * @Data 2017-5-24 下午4:27:42
 * @Description 
 */
public class supertest {
	static class Father {
		  Father(){
			System.out.println("father");  
			  };
		}

	static	class Son {
		  Son(){
			  System.out.println("son");
			 };
		}
		
		public static  void main(String[] args){
			Father fa= new  Father();
			Son so=new Son();
		}
		
}
