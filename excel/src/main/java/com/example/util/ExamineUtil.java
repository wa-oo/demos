package com.example.util;

/**
 * 字符串校验
 */
public class ExamineUtil {
    public static String examine(String s){
        //\s 可以匹配空格、制表符、换页符等空白字符的其中任意一个
        String string = s.replaceAll("\\s*", "");
        return string;
    }
}

