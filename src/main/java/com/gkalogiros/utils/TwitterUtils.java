package com.gkalogiros.utils;

import com.gkalogiros.models.Tweet;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TwitterUtils {

    private static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";

    private static final String TEXT = "text";
    private static final String CREATED_AT = "created_at";
    private static final String USER = "user";
    private static final String NAME = "name";

    public static String[] tokenize()
    {
        return new String[]{};
    }

    public static Tweet buildTweetFromJson(String tweet)
            throws ParseException, java.text.ParseException {

        JSONParser parser = new JSONParser();

        // Tweet Message
        JSONObject json = (JSONObject) parser.parse(tweet);
        String text = (String) json.get(TEXT);
        String twitterDate = (String) json.get(CREATED_AT);

        // User ID
        JSONObject user = (JSONObject) json.get(USER);
        String userName = (String) user.get(NAME);

        Date date = TwitterUtils.convertTwitterToJavaDate(twitterDate);
        return new Tweet(userName, date, text);
    }

    public static Date convertTwitterToJavaDate(String twitterDate) throws java.text.ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
        sf.setLenient(true);
        return sf.parse(twitterDate);
    }
}
