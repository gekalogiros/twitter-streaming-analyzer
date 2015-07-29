package com.gkalogiros.twitter;

import com.gkalogiros.models.Tweet;

import java.util.List;

public interface TwitterSearchClient {

    public void startStreamWithFilter(String term);
    public List<Tweet> search(String term);
    public void close();

}
