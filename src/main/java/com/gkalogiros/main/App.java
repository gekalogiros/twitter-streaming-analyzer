package com.gkalogiros.main;

import com.gkalogiros.models.Tweet;
import com.gkalogiros.store.InMemoryStoreImpl;
import com.gkalogiros.store.Store;
import com.gkalogiros.twitter.TwitterProperties;
import com.gkalogiros.twitter.TwitterSearchClientImpl;
import com.gkalogiros.utils.TwitterUtils;
import org.json.simple.parser.ParseException;

public class App {

    public static final String TERM = "Holidays";

    private Store<Tweet> store;
    private TwitterSearchClientImpl client;

    public App()
    {
        this.store  = new InMemoryStoreImpl();
        this.client = new TwitterSearchClientImpl();
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
            Tweet tweet = TwitterUtils.buildTweetFromJson(msg);

            // If it is valid, index the contents
            if (tweet.isValid()) store.put(tweet);
        }
    }
}
