package com.gkalogiros.twitter;

import com.gkalogiros.models.Tweet;
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

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class TwitterSearchClientImpl implements TwitterSearchClient {

    /*
     * ====================================================================================================
     * INSTANCE VARIABLES
     * ====================================================================================================
     */

    /** Set up your blocking queues: Be sure to size these properly based on expected TPS of your stream */
    BlockingQueue<String> msgQueue;

    /** Declare the host you want to connect to, the endpoint, and authentication (basic auth or oauth) */
    Hosts hosebirdHosts;

    /** Stream Endpoint */
    StatusesFilterEndpoint endpoint;

    /** Client onject */
    Client client;

    public TwitterSearchClientImpl()
    {
        TwitterProperties.instance();
        msgQueue = new LinkedBlockingQueue<String>(100000);
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
        List<String> terms = Lists.newArrayList("Greece");
        endpoint.trackTerms(terms);

        ClientBuilder builder = new ClientBuilder()
                .name("Kahoot Test")
                .hosts(hosebirdHosts)
                .authentication(authentication())
                .endpoint(endpoint)
                .processor(new StringDelimitedProcessor(msgQueue));

        /* Construct Twitter Streaming API Client */
        client = builder.build();

        /* Connect to Stream */
        client.connect();
    }

    @Override
    public void close() {
        if (null != client)
            client.stop();
        else
            System.out.println("Client has not been started.");
    }

    @Override
    public List<Tweet> search(String term) {

        return null;
    }

    /*
     * ====================================================================================================
     * PRIVATE
     * ====================================================================================================
     */
    private Authentication authentication()
    {
        return new OAuth1(
                TwitterProperties.consumerKey, // Consumer-key
                TwitterProperties.consumerSecret, // Consumer-Secret
                TwitterProperties.appToken, // app token
                TwitterProperties.appSecret); // app secret
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