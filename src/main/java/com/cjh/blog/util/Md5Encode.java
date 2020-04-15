package com.cjh.blog.util;


import org.apache.commons.codec.digest.DigestUtils;

public class Md5Encode {

	//md5加密
	public static String md5Encode(String password){//byte[] ("abc")-->String("123134fjdf")
		byte[] input=password.getBytes();
		String md5Hex = DigestUtils.md5Hex(input);
		
		StringBuffer sb=new StringBuffer(md5Hex);
		sb.insert(0, "m");
		sb.insert((sb.length())/2,"d");
		sb.append("5");
        return sb.toString();
    }
	
	public static void main(String[] args) {
		String str="123456";
		str=md5Encode(str);
		System.out.println("加密后的结果："+str);
	}

}
