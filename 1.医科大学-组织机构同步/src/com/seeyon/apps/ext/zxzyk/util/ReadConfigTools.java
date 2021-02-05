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

    private String orgAccountId = "";
    private String orgPostId = "";

    private String durl = "";
    private String dusername = "";
    private String dpassword = "";
    private String ddriver = "";


    private String restUrl = "";
    private String restUsername = "";
    private String restPwd = "";

    public ReadConfigTools() {
        String path = Thread.currentThread().getContextClassLoader().getResource("xzykConf/xzykPlugin.properties").getPath();
        File file = new File(path);
        InputStream inputStream = null;
        try {
            properties = new Properties();
            inputStream = new FileInputStream(file);
            properties.load(new InputStreamReader(inputStream, "UTF-8"));

            this.orgAccountId = properties.getProperty("xzyk.orgAccountId");
            this.orgPostId = properties.getProperty("xzyk.orgPostId");
            this.durl = properties.getProperty("xzyk.midDataLink.url");
            this.dusername = properties.getProperty("xzyk.midDataLink.username");
            this.dpassword = properties.getProperty("xzyk.midDataLink.password");
            this.ddriver = properties.getProperty("xzyk.midDataLink.driver");
            this.restUrl = properties.getProperty("xzyk.restInfo.url");
            this.restUsername = properties.getProperty("xzyk.restInfo.username");
            this.restPwd = properties.getProperty("xzyk.restInfo.password");
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

    public String getOrgAccountId() {
        return orgAccountId;
    }

    public void setOrgAccountId(String orgAccountId) {
        this.orgAccountId = orgAccountId;
    }

    public String getOrgPostId() {
        return orgPostId;
    }

    public void setOrgPostId(String orgPostId) {
        this.orgPostId = orgPostId;
    }

    public String getDurl() {
        return durl;
    }

    public void setDurl(String durl) {
        this.durl = durl;
    }

    public String getDusername() {
        return dusername;
    }

    public void setDusername(String dusername) {
        this.dusername = dusername;
    }

    public String getDpassword() {
        return dpassword;
    }

    public void setDpassword(String dpassword) {
        this.dpassword = dpassword;
    }

    public String getDdriver() {
        return ddriver;
    }

    public void setDdriver(String ddriver) {
        this.ddriver = ddriver;
    }

    public String getRestUrl() {
        return restUrl;
    }

    public void setRestUrl(String restUrl) {
        this.restUrl = restUrl;
    }

    public String getRestUsername() {
        return restUsername;
    }

    public void setRestUsername(String restUsername) {
        this.restUsername = restUsername;
    }

    public String getRestPwd() {
        return restPwd;
    }

    public void setRestPwd(String restPwd) {
        this.restPwd = restPwd;
    }

    public Properties getProperties() {
        return properties;
    }

    public void setProperties(Properties properties) {
        this.properties = properties;
    }
}
