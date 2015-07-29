package com.gkalogiros.twitter;

import org.junit.Test;
import static org.junit.Assert.assertTrue;

public class PropertiesTests {

    @Test
    public void thatPropertiesCanBeLoader(){
        TwitterProperties props = TwitterProperties.instance();
        assertTrue(TwitterProperties.appToken.equalsIgnoreCase(""));
        assertTrue(TwitterProperties.appSecret.equalsIgnoreCase(""));
        assertTrue(TwitterProperties.consumerKey.equalsIgnoreCase(""));
        assertTrue(TwitterProperties.consumerSecret.equalsIgnoreCase(""));

    }
}
