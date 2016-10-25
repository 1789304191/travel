package com.xinding.travel.util;

/**
 * @description：驻户用户信息加密
 * @author Stoney Liu,2014年8月6日 下午11:16:45
 */
public final class CodeUtil {
    private static String salt = "wonders";

    public static String md5(String inbuf) {
        MD5 md = new MD5();
        return md.getMD5ofStr(inbuf);
    }

    public static String encodePwd(String username, String password) {
        return md5(username + password + salt);
    }

    public static void main(String[] arg) {
        System.out.println(encodePwd("123@qq.com", "1"));
    }
        
}
