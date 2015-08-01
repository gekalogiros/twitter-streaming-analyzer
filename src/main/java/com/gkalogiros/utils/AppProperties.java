package com.gkalogiros.utils;

import java.io.InputStream;
import java.util.Properties;

public class AppProperties {

    static private AppProperties instance = null;
    static public String appToken, appSecret, consumerKey, consumerSecret, searchTerm, alchemyKey;

    private AppProperties()
    {
        try
        {
            ClassLoader classloader = Thread.currentThread().getContextClassLoader();
            InputStream is = classloader.getResourceAsStream("app.properties");
            Properties props = new Properties();
            props.load(is);
            parseProperties(props);
        }
        catch(Exception e)
        {
            System.out.println("error" + e);
        }
    }

    private void parseProperties(Properties props)
    {
        appToken = props.getProperty("app.token");
        appSecret = props.getProperty("app.secret");
        consumerKey = props.getProperty("consumer.key");
        consumerSecret = props.getProperty("consumer.secret");
        searchTerm = props.getProperty("search.term");
        alchemyKey = props.getProperty("alchemy.key");
    }

    static public AppProperties instance()
    {
        if (instance == null)
        {
            instance = new AppProperties();
        }
        return instance;
    }
}
