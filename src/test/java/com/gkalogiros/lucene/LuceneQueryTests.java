package com.gkalogiros.lucene;

import com.gkalogiros.models.Statistics;
import com.gkalogiros.models.Tweet;
import com.gkalogiros.store.InMemoryStoreImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import static org.junit.Assert.assertTrue;

public class LuceneQueryTests {

    static Tweet tweet1, tweet2, tweet3, tweet4;
    static SimpleDateFormat sf;

    static {
        sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        sf.setLenient(true);
    }

    @BeforeClass
    public static void setup() throws ParseException {


        // Date date1 = new Date(1373414400000l);
        Date date1 = sf.parse("Wed Jul 10 00:00:00 +0000 2013");
        System.out.println(date1.getTime());

        //Date date2 = new Date(1404950400000l);
        Date date2 = sf.parse("Thu Jul 10 00:00:00 +0000 2014");

        //Date date3 = new Date(1436486400000l);
        Date date3 = sf.parse("Fri Jul 10 00:00:00 +0000 2015");

        //Date date4 = new Date(1436572800000l);
        Date date4 = sf.parse("Sat Jul 11 00:00:00 +0000 2015");

        tweet1 = new Tweet("bizz", date1, "I enjoy music");
        tweet2 = new Tweet("buzz", date2, "Listening to Massive Attack.");
        tweet3 = new Tweet("hazz", date3, "Enjoying being at music festivals!");
        tweet4 = new Tweet("hazz", date4, "This is the second tweet from hazz");
    }

    @Test
    public void thatAllTweetsQueryWorks()
    {
        InMemoryStoreImpl store = new InMemoryStoreImpl();
        store.put(tweet1);
        store.put(tweet2);
        store.put(tweet3);

        Collection<Object> results = (Collection<Object>) store.retrieve(new AllTweetsQuery());
        assertTrue(results.size() == 3);
    }


    @Test
    public void thatStatsDateRangeQueryWorks() throws ParseException {

        Date from = sf.parse("Thu Jul 08 00:00:00 +0000 2013");

        InMemoryStoreImpl store = new InMemoryStoreImpl();
        store.put(tweet1);
        store.put(tweet2);
        store.put(tweet3);
        store.put(tweet4);

        Statistics stats = (Statistics) store.retrieve(new StatsDateRangeQuery(from, new Date()));

        assertTrue(stats.getTotalTweets() == 4);
        assertTrue(stats.getMostFrequentTerms(1).contains("music"));
    }

}
