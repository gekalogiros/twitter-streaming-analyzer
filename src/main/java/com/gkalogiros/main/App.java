package com.gkalogiros.main;

import com.gkalogiros.apis.TwitterSearchClient;
import com.gkalogiros.apis.TwitterSearchClientImpl;
import com.gkalogiros.models.Tweet;
import com.gkalogiros.apis.SentimentAnalyzer;
import com.gkalogiros.apis.SentimentAnalyzerImpl;
import com.gkalogiros.store.InMemoryStoreImpl;
import com.gkalogiros.store.Store;
import com.gkalogiros.utils.AppProperties;
import org.json.simple.parser.ParseException;

public class App {

    private static final AppProperties props = AppProperties.instance();
    private static final String TERM = AppProperties.searchTerm;
    private static final String ALCHEMY_KEY = AppProperties.alchemyKey;
    private Store<Tweet> store;
    private TwitterSearchClient client;
    private SentimentAnalyzer analyzer;

    public App()
    {
        this.store  = new InMemoryStoreImpl();
        this.client = new TwitterSearchClientImpl();
        this.analyzer = new SentimentAnalyzerImpl(ALCHEMY_KEY);

        Thread thread = new Thread(new Monitoring(store));
        thread.start();
    }

    public void run() throws InterruptedException, ParseException, java.text.ParseException {

        client.startStreamWithFilter(TERM);

        while (!client.getClient().isDone())
        {
            // Take tweet message from queue.
            String msg = client.getMsgQueue().take();

            // Wrap tweet around a Tweet Class.
            Tweet tweet = client.getTweetFromJson(msg);

            // Find sentiment
            Tweet tweetWithSentiment = analyzer.addSentiment(tweet);

            // If it is valid, index the contents
            if (tweet.isValid()) store.put(tweetWithSentiment);
        }
    }
}
