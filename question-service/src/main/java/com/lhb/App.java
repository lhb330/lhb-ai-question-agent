package com.lhb;

import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.lhb.constants.UserConst;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        String pwd = SecureUtil.sha256("123456");
        String s = DigestUtil.sha256Hex(UserConst.SALT + "123456");
        System.out.println( pwd );
        System.out.println( pwd.length() );
        System.out.println( s);
        System.out.println( s.length() );
    }
}
