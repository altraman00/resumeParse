package com.mdl.zhaopin.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xiekun
 * @ClassName: RegexUtils
 * @Description 正则表达式工具类，验证数据是否符合规范
 * @date 2017年11月1日 上午10:40:21
 */
public class RegexUtils {

    /**
     * @param @param  str
     * @param @param  regex
     * @param @return 设定文件
     * @return boolean    返回类型
     * @throws
     * @Title: find
     * @Description 判断字符串是否符合正则表达式
     */
    public static boolean find(String str, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(str);
        return m.find();
    }

    /**
     * @param @param  email
     * @param @return 设定文件
     * @return boolean    返回类型
     * @throws
     * @Title: isEmail
     * @Description 判断输入的字符串是否符合Email格式.
     */
    public static boolean isEmail(String email) {
        if (email == null || email.length() < 1 || email.length() > 256) {
            return false;
        }
        Pattern pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
        return pattern.matcher(email).matches();
    }

    /**
     * @param @param  value
     * @param @return 设定文件
     * @return boolean    返回类型
     * @throws
     * @Title: isChinese
     * @Description 判断输入的字符串是否为纯汉字
     */
    public static boolean isChinese(String value) {
        Pattern pattern = Pattern.compile("[\u0391-\uFFE5]+$");
        return pattern.matcher(value).matches();
    }

    /**
     * @param @param  value
     * @param @return 设定文件
     * @return boolean    返回类型
     * @throws
     * @Title: isDouble
     * @Description 判断是否为浮点数，包括double和float
     */
    public static boolean isDouble(String value) {
        Pattern pattern = Pattern.compile("^[-\\+]?\\d+\\.\\d+$");
        return pattern.matcher(value).matches();
    }

    /**
     * @param @param  value
     * @param @return 设定文件
     * @return boolean    返回类型
     * @throws
     * @Title: isInteger
     * @Description 判断是否为整数
     */
    public static boolean isInteger(String value) {
        Pattern pattern = Pattern.compile("^[-\\+]?[\\d]+$");
        return pattern.matcher(value).matches();
    }

    /**
     * @param @param  con
     * @param @param  reg
     * @param @return 设定文件
     * @return String    返回类型
     * @throws
     * @Title: getValue
     * @Description 得一正则表达对应的内容
     */
    private String getValue(String con, String reg) {
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(con);
        String res = "";
        while (m.find()) {
            res = m.group(1);
        }
        return res;
    }


    public static String replaceBlank(String str) {
        String dest = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
