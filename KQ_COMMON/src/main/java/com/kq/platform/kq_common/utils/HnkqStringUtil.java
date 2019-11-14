package com.kq.platform.kq_common.utils;


import java.util.regex.Pattern;

/**
 * 字符串处理工具类(基于Apache StringUtils)
 * <p>
 * 1.判断（包括NULL和空串、是否是空白字符串等）<br>
 * 2.默认值<br>
 * 3.去空白（trim）<br>
 * 4.比较<br>
 * 5.字符类型判断（是否只包含数字、字母）<br>
 * 6.大小写转换（首字母大小写等）<br>
 * 7.字符串分割<br>
 * 8.字符串连接<br>
 * 9.字符串查找<br>
 * 10.取子串<br>
 * 11.删除字符<br>
 * 12.字符串比较<br>
 * 13.特定类型字符串校验<br>
 * 14.生成随机字符串<br>
 * </p>
 *
 * @author 覃忠君
 * @Copyright © 湖南科权
 */
public class HnkqStringUtil {

    /* ============================================================================ */
    /*  常量和singleton。                                                           */
    /* ============================================================================ */

    /**
     * 正则表达式：验证用户名
     */
    public static final String REGEX_USERNAME = "^[a-zA-Z]\\w{5,17}$";

    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-z0-9]{6,16}$";

    /**
     * 正则表达式：验证支付密码
     */
    public static final String REGEX_PAY_PASSWORD = "^[0-9]{6}$";

    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^((13[0-9])|(15[^4,\\D])|(14[57])|(17[0])|(18[0,0-9]))\\d{8}$";

    /**
     * 正则表达式：验证邮箱
     */
    public static final String REGEX_EMAIL = "^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";


    /**
     * 正则表达式：验证身份证
     */
    public static final String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";


    /* ============================================================================ */
    /*  特定字符串校验                                                                */
    /*                                                                              */
    /* ============================================================================ */

    /**
     * 校验用户名(只能包含大小写字母、下划线，5-17位)
     *
     * @param username 用户名
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isUsername(String username) {
        return Pattern.matches(REGEX_USERNAME, username);
    }

    /**
     * 校验密码(只能包含字母、数字，6-16位)
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPassword(String password) {
        return Pattern.matches(REGEX_PASSWORD, password);
    }

    /**
     * 校验支付密码(数字，6位)
     *
     * @param password
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isPayPassword(String password) {
        return Pattern.matches(REGEX_PAY_PASSWORD, password);
    }

    /**
     * 校验手机号
     *
     * @param mobile
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
        return Pattern.matches(REGEX_MOBILE, mobile);
    }

    /**
     * 校验邮箱
     *
     * @param email
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isEmail(String email) {
        return Pattern.matches(REGEX_EMAIL, email);
    }

    /**
     * 校验身份证
     *
     * @param idCard
     * @return 校验通过返回true，否则返回false
     */
    public static boolean isIDCard(String idCard) {
        return Pattern.matches(REGEX_ID_CARD, idCard);
    }


}