package com.gkalogiros.apis;

import com.gkalogiros.models.Tweet;
import com.gkalogiros.utils.AppProperties;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.twitter.hbc.ClientBuilder;
import com.twitter.hbc.core.Client;
import com.twitter.hbc.core.Constants;
import com.twitter.hbc.core.Hosts;
import com.twitter.hbc.core.HttpHosts;
import com.twitter.hbc.core.endpoint.StatusesFilterEndpoint;
import com.twitter.hbc.core.processor.StringDelimitedProcessor;
import com.twitter.hbc.httpclient.auth.Authentication;
import com.twitter.hbc.httpclient.auth.OAuth1;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterSearchClientImpl implements TwitterSearchClient {

    /*
     * ====================================================================================================
     * CONSTANTS
     * ====================================================================================================
     */
    private static final String DATE_FORMAT = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
    private static final String CLIENT_NAME = "twitter-search-example";
    private static final String TEXT = "text";
    private static final String CREATED_AT = "created_at";
    private static final String USER = "user";
    private static final String NAME = "name";

    private static final int QUEUE_SIZE = 100000;
    /*
     * ====================================================================================================
     * INSTANCE VARIABLES
     * ====================================================================================================
     */
    private BlockingQueue<String> msgQueue;

    /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
    private Hosts hosebirdHosts;

    /** Stream Endpoint */
    private StatusesFilterEndpoint endpoint;

    /** Client for accessing Twitter's Streaming api */
    private Client client;

    public TwitterSearchClientImpl()
    {
        msgQueue = new LinkedBlockingQueue<String>(QUEUE_SIZE);
        hosebirdHosts = new HttpHosts(Constants.STREAM_HOST);
        endpoint = new StatusesFilterEndpoint();
    }

    /*
     * ====================================================================================================
     * PUBLIC
     * ====================================================================================================
     */

    @Override
    public void startStreamWithFilter(String term) {
        /** Define some search Terms */
        List<String> terms = Lists.newArrayList(term);

        if (!Strings.isNullOrEmpty(term))
        {
            startTrackingTerms(terms);
        }
        else
        {
            System.err.println("You haven't provided a search term!");
        }
    }

    @Override
    public void close() {
        if (null != client)
            client.stop();
        else
            System.out.println("Client has not been started.");
    }

    @Override
    public Tweet getTweetFromJson(String tweet) throws ParseException, java.text.ParseException {
        JSONParser parser = new JSONParser();

        // Tweet Message
        JSONObject json = (JSONObject) parser.parse(tweet);
        String text = (String) json.get(TEXT);
        String twitterDate = (String) json.get(CREATED_AT);

        // User ID
        JSONObject user = (JSONObject) json.get(USER);
        String userName = (String) user.get(NAME);

        // Date created
        Date date = convertTwitterToJavaDate(twitterDate);

        return new Tweet(userName, date, text);
    }

    /*
     * ====================================================================================================
     * PRIVATE
     * ====================================================================================================
     */
    private void startTrackingTerms(List terms)
    {
        endpoint.trackTerms(terms);

        ClientBuilder builder = new ClientBuilder()
                .name(CLIENT_NAME)
                .hosts(hosebirdHosts)
                .authentication(authentication())
                .endpoint(endpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

            /* Construct Twitter Streaming API Client */
        client = builder.build();

            /* Connect to Stream */
        client.connect();
    }

    private Authentication authentication()
    {
        return new OAuth1(
                AppProperties.consumerKey,    // Consumer-key
                AppProperties.consumerSecret, // Consumer-Secret
                AppProperties.appToken,       // app token
                AppProperties.appSecret);     // app secret
    }

    public static Date convertTwitterToJavaDate(String twitterDate) throws java.text.ParseException {
        SimpleDateFormat sf = new SimpleDateFormat(DATE_FORMAT);
        sf.setLenient(true);
        return sf.parse(twitterDate);
    }

    /*
     * ====================================================================================================
     * GETTERS / SETTERS
     * ====================================================================================================
     */
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public BlockingQueue<String> getMsgQueue() {
        return msgQueue;
    }

    public void setMsgQueue(BlockingQueue<String> msgQueue) {
        this.msgQueue = msgQueue;
    }
}
