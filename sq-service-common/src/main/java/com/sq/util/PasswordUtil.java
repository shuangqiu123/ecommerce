package com.sq.util;

import org.apache.commons.codec.digest.DigestUtils;

public class PasswordUtil {

    public final static String SALT = "sq";

    /**
     *
     * @param text the thing to be encoded
     * @return
     * @throws Exception
     */
    public static String encode(String text) throws Exception {

        String encodeStr= DigestUtils.md5Hex(text+SALT);
        return encodeStr;
    }

    /**
     * verify text == md5
     * @param text
     * @param md5
     * @return
     * @throws Exception
     */
    public static boolean verify(String text, String md5) throws Exception {
        String md5Text = encode(text);
        if(md5Text.equalsIgnoreCase(md5))
        {
            return true;
        }
        return false;
    }
}
