package com.gkalogiros.apis;

import com.gkalogiros.models.Tweet;
import com.twitter.hbc.core.Client;
import org.json.simple.parser.ParseException;

import java.util.concurrent.BlockingQueue;

public interface TwitterSearchClient {
    public void startStreamWithFilter(String term);
    public void close();
    public Client getClient();
    public BlockingQueue<String> getMsgQueue();
    public Tweet getTweetFromJson(String tweet) throws ParseException, java.text.ParseException;
}
