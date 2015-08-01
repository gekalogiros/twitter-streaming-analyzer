package com.gkalogiros.twitter;

import com.gkalogiros.apis.TwitterSearchClientImpl;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import static org.junit.Assert.*;

public class TwitterSearchClientImplTests {

    @Test
    public void thatTwitterStreamProducesMessages() throws InterruptedException, ParseException {
        TwitterSearchClientImpl client = new TwitterSearchClientImpl();
        client.startStreamWithFilter("Greece");

        long start = System.currentTimeMillis();
        while (!client.getClient().isDone())
        {
            if (System.currentTimeMillis() - start > 120000) return;
        }

        assertTrue(client.getMsgQueue().size() > 0);
    }

}
