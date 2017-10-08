package com.test.getFile;

import java.util.Map;

/**
 * 
* Title: loadPropertiesTest
* Description: 读取文件数据测试
* Version:1.0.0  
* @author pancm
* @date 2017年9月22日
 */
public class loadPropertiesTest {
	private static Map<String, String> conf; 
	public static void main(String[] args) {
		conf=getPropertiesTest.getAppSettings();
		System.out.println("本机IP:"+conf.get("localhost"));
		System.out.println("tomcat 端口:"+conf.get("tomcatPort"));
	}

}
