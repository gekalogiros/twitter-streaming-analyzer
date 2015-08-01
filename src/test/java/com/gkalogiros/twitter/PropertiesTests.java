package com.gkalogiros.twitter;

import com.gkalogiros.utils.AppProperties;
import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PropertiesTests {

    @Test
    public void thatPropertiesCanBeLoader(){
        AppProperties props = AppProperties.instance();
        assertTrue(AppProperties.appToken.equalsIgnoreCase(""));
        assertTrue(AppProperties.appSecret.equalsIgnoreCase(""));
        assertTrue(AppProperties.consumerKey.equalsIgnoreCase(""));
        assertTrue(AppProperties.consumerSecret.equalsIgnoreCase(""));

    }
}
