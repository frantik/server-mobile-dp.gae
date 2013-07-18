/*
 * Copyright ⓒ 2011 Tuck & Company Inc. All Rights Reserved
 */
package com.dvdprime.server.mobile.util;


/**
 * 문자열 관련 유틸
 *
 * @author 작은광명
 *
 */
public class StringUtil
{
    // ----------------------------------------------------------------------
    // 문자열 비교
    // ----------------------------------------------------------------------
    /**
     * <p>
     * 두 문자열(String)이 일치하면 <code>true</code>을 반환한다.
     * </p>
     * 
     * <pre>
     * StringUtil.equals(null, null)   = true
     * StringUtil.equals(null, "")     = false
     * StringUtil.equals("", null)     = false
     * StringUtil.equals(null, "han")  = false
     * StringUtil.equals("han", null)  = false
     * StringUtil.equals("han", "han") = true
     * StringUtil.equals("han", "HAN") = false
     * </pre>
     * 
     * @see java.lang.String#equals(Object)
     * @param str1
     *            첫번째 문자열
     * @param str2
     *            두번째 문자열
     * @return 문자열(String)이 일치하면 <code>true</code>
     */
    public static boolean equals(String str1, String str2)
    {
        return str1 == null ? str2 == null : str1.equals(str2);
    }
    
    /**
     * <p>
     * 대소문자를 무시한, 두 문자열(String)이 일치하면 <code>true</code>을 반환한다.
     * </p>
     * 
     * <pre>
     * StringUtil.equalsIgnoreCase(null, null)   = true
     * StringUtil.equalsIgnoreCase(null, "")     = false
     * StringUtil.equalsIgnoreCase("", null)     = false
     * StringUtil.equalsIgnoreCase(null, "han")  = false
     * StringUtil.equalsIgnoreCase("han", null)  = false
     * StringUtil.equalsIgnoreCase("han", "han") = true
     * StringUtil.equalsIgnoreCase("han", "HAN") = true
     * </pre>
     * 
     * @see java.lang.String#equalsIgnoreCase(String)
     * @param str1
     *            첫번째 문자열
     * @param str2
     *            두번째 문자열
     * @return 대소문자를 무시한 문자열(String)이 일치하면 <code>true</code>
     */
    public static boolean equalsIgnoreCase(String str1, String str2)
    {
        return str1 == null ? str2 == null : str1.equalsIgnoreCase(str2);
    }
    
}