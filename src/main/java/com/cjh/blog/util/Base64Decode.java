package com.cjh.blog.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

//base64解密
public class Base64Decode {
    public static byte[] base64Decode(String input){
        byte[] result=null;
        try {
            Class clz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
            Method method=clz.getMethod("decode", String.class);
            result= (byte[]) method.invoke(null,input);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return result;
    }
}
