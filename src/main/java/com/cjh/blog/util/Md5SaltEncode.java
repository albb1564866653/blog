package com.cjh.blog.util;

import org.apache.shiro.crypto.hash.Md5Hash;

public class Md5SaltEncode {

	/**
	 * md5加密
	 * @param str	密码
	 * @param salt	盐值（佐料），建议值是唯一的
	 * @return
	 */
	public static String md5Hash(String str,String salt) {
		return new Md5Hash(str, salt).toString();
	}

	/**
	 * md5加密
	 * @param str	密码
	 * @param salt	佐料
	 * @param count	加密次数
	 * @return
	 */
	public static String md5Hash(String str,String salt,int count) {
		return new Md5Hash(str, salt,count).toString();
	}

	
	public static void main(String[] args) {
		String pwd="666455";
		System.out.println("md5加密1："+md5Hash(pwd, "admin"));
		System.out.println("md5加密2："+md5Hash(pwd, "cjh111111",3));
	}	

}
