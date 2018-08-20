package com.example.utils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

public class GetPropertiesUtil {

    public static Properties getProperties() {
        Properties properties = new Properties();
        try {
            properties.load(new InputStreamReader(GetPropertiesUtil.class.getClassLoader().getResourceAsStream("weixin.properties"), "UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
