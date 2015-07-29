package com.gkalogiros.store;

import com.gkalogiros.models.Tweet;
import org.apache.lucene.search.MatchAllDocsQuery;
import org.apache.lucene.search.Query;
import org.junit.BeforeClass;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class InMemoryImplTests {

    static Tweet tweet;
    static SimpleDateFormat sf;

    static {
        sf = new SimpleDateFormat("EEE MMM dd HH:mm:ss ZZZZZ yyyy");
        sf.setLenient(true);
    }

    @BeforeClass
    public static void setup() throws ParseException {
        Date date1 = sf.parse("Wed Jul 10 00:00:00 +0000 2013");
        System.out.println(date1.getTime());
        tweet = new Tweet("DummyUser", date1, "I want to index this tweet.");
    }

    @Test
    public void thatAllTweetsQueryWorks()
    {
        Store store = new InMemoryStoreImpl();
        store.put(tweet);

//        Query q = new MatchAllDocsQuery;
//        store.retrieve(q);
    }
}
