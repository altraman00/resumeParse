package com.mdl.zhaopin.service.read.impl;


import com.mdl.zhaopin.service.read.ReadFile;
import com.mdl.zhaopin.utils.RegexUtils;

import java.util.regex.Pattern;

public class ReadHtml implements ReadFile {

    @Override
    public String read(String Path) {

        ReadTxt readTxt = new ReadTxt();
        // 读取html的所有内容包含标签
        String htmlStr = readTxt.read(Path);
        // 去除html里面的标签，提取文本
        String buffer = getTextFromHtml(htmlStr);

        return RegexUtils.replaceBlank(buffer);
    }

    /**
     * @param inputString
     * @Title: getTextFromHtml
     * @Description: 读取html文件内容
     * @return: 读出的html的内容
     */
    public static String getTextFromHtml(String inputString) {
        // 含html标签的字符串
        String htmlStr = inputString;
        String textStr = "";
        Pattern p_script;
        java.util.regex.Matcher m_script;
        Pattern p_style;
        java.util.regex.Matcher m_style;
        Pattern p_html;
        java.util.regex.Matcher m_html;
        try {
            // 定义script的正则表达式{或<script[^>]*?>[\\s\\S]*?<\\/script>
            String regEx_script = "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
            // 定义style的正则表达式{或<style[^>]*?>[\\s\\S]*?<\\/style>
            String regEx_style = "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
            // 定义HTML标签的正则表达式
            String regEx_html = "<[^>]+>";
            p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
            m_script = p_script.matcher(htmlStr);
            // 过滤script标签
            htmlStr = m_script.replaceAll("");
            p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
            m_style = p_style.matcher(htmlStr);
            // 过滤style标签
            htmlStr = m_style.replaceAll("");
            p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
            m_html = p_html.matcher(htmlStr);
            // 过滤html标签
            htmlStr = m_html.replaceAll("");
            textStr = htmlStr;
        } catch (Exception e) {
            System.err.println("Html2Text: " + e.getMessage());
        }

        // 剔除空格行
        textStr = textStr.replaceAll("[ ]+", " ");
        textStr = textStr.replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "");
        // 返回文本字符串
        return textStr;
    }

}
