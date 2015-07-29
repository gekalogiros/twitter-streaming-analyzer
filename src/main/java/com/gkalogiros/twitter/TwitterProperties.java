package com.gkalogiros.twitter;

import java.io.InputStream;
import java.util.Properties;

public class TwitterProperties {

    static private TwitterProperties instance = null;

    static public String appToken = null;
    static public String appSecret = null;
    static public String consumerKey = null;
    static public String consumerSecret = null;

    static
    {

    }

    private TwitterProperties()
    {
        try
        {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("app.properties");
            Properties props = new Properties();
            props.load(is);
            appToken = props.getProperty("app.token");
            appSecret = props.getProperty("app.secret");
            consumerKey = props.getProperty("consumer.key");
            consumerSecret = props.getProperty("consumer.secret");
        }
        catch(Exception e)
        {
            System.out.println("error" + e);
        }
    }

    static public TwitterProperties instance(){
        if (instance == null) {
            instance = new TwitterProperties();
        }
        return instance;
    }

}
