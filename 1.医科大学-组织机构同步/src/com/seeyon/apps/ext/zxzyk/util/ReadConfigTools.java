package com.seeyon.apps.ext.zxzyk.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 周刘成   2019/7/24
 */
public class ReadConfigTools {
    private Properties properties;

    public ReadConfigTools() {
        String path = Thread.currentThread().getContextClassLoader().getResource("xzykConf/xzykPlugin.properties").getPath();
        File file = new File(path);
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(file);
            this.properties.load(new InputStreamReader(inputStream, "UTF-8"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public String getString(String key) {
        if ((key == null) || (key.equals("")) || (key.equals("null"))) {
            return "";
        }
        String result = "";
        try {
            result = properties.getProperty(key);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
