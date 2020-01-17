package com.mdl.zhaopin.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MatcherAssist {

    /**
     * 获取匹配的所有字符串
     *
     * @param content
     * @param sPattern
     * @return
     */
    public static List<String> getMatcherStrs(String content, String sPattern) {
        Pattern p = Pattern.compile(sPattern);
        Matcher m = p.matcher(content);
        List<String> result = new ArrayList<String>();
        while (m.find()) {
            result.add(m.group());
        }
        return result;
    }

    /**
     * 获取单个匹配的字符串
     *
     * @param content
     * @param sPattern
     * @return
     */
    public static String getMatcherStr(String content, String sPattern) {
        List<String> strs = getMatcherStrs(content, sPattern);
        if (strs.size() > 0) {
            return strs.get(0);
        } else {
            return "";
        }

    }

    /**
     * 获取匹配的所有字符串
     *
     * @param content
     * @param sPattern
     * @return
     */
    public static List<String> getMatcherStrsAmong(String content, String sPattern) {
        Pattern p = Pattern.compile(sPattern);
        Matcher m = p.matcher(content);
        List<String> result = new ArrayList<String>();
        while (m.find()) {
            result.add(m.group(1));
        }
        return result;
    }

    public static String getMatcherStrAmong(String content, String sPattern) {
        List<String> strs = getMatcherStrsAmong(content, sPattern);
        if (strs.size() > 0) {
            return strs.get(0);
        } else {
            return "";
        }
    }

    /**
     * 获取所有匹配的日期，年月
     *
     * @param content
     * @param splitStr
     * @return
     */
    public static List<String> getDatesYM(String content, String splitStr) {
        return getMatcherStrs(content, "\\d{4}\\" + splitStr + "\\d{1,2}");
    }

    /**
     * 获取所有匹配的日期，年月日
     *
     * @param content
     * @param splitStr
     * @return
     */
    public static List<String> getDatesYMD(String content, String splitStr) {
        return getMatcherStrs(content, "\\d{4}\\" + splitStr + "\\d{1,2}\\" + splitStr + "\\d{1,2}");
    }

    /**
     * 获取第一个匹配的日期,年月
     *
     * @param content
     * @param splitStr
     * @return
     */
    public static String getFirstDateYM(String content, String splitStr) {
        return getDatesYM(content, splitStr).get(0);
    }

    /**
     * 获取第一个匹配的日期,年月日
     *
     * @param content
     * @param splitStr
     * @return
     */
    public static String getFirstDateYMD(String content, String splitStr) {
        return getDatesYMD(content, splitStr).get(0);
    }

}
