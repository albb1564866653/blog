package com.cjh.blog.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//base64加密
public class Base64Encode {
    public static String base64Encode(byte[] input) {
        String result = null;
        try {
            Class clz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method method = clz.getMethod("encode", byte[].class);
            result = (String) method.invoke(null, input);
        } catch (ClassNotFoundException | NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String[] args) {
        String pwd = "123456abc";
        System.out.println(pwd + "-长度：" + pwd.length());
        pwd = base64Encode(pwd.getBytes());
        System.out.println(pwd + "-长度：" + pwd.length());
    }
}
