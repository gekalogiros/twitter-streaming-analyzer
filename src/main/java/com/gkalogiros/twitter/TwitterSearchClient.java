package com.gkalogiros.twitter;

import com.gkalogiros.models.Tweet;
import com.twitter.hbc.core.Client;

import java.util.List;
import java.util.concurrent.BlockingQueue;

public interface TwitterSearchClient {
    public void startStreamWithFilter(String term);
    public List<Tweet> search(String term);
    public void close();
    public Client getClient();
    public BlockingQueue<String> getMsgQueue();
}
