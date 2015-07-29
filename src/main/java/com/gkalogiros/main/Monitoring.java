package com.gkalogiros.main;

import com.gkalogiros.lucene.AllTweetsQuery;
import com.gkalogiros.lucene.LuceneQuery;
import com.gkalogiros.lucene.StatsDateRangeQuery;
import com.gkalogiros.models.Statistics;
import com.gkalogiros.store.Store;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.Date;

/**
 * This is a class that queries the Lucene index every
 * 5 seconds and gets back useful statistics about the
 * tweet messages that have been indexed.
 */
public class Monitoring implements Runnable {

    /*
     * ====================================================================================================
     * CONSTANTS
     * ====================================================================================================
     */

    private static final String SEPARATOR = "---------------------------------------------------------------------";
    private static final String TOTAL_NUMBER_OF_DOCUMENTS = "Total number of tweets so far is: ";
    private static final String TOTAL_NUMBER_OF_TWEETS_MSG = "Total number of tweets matching the search term in the last %d minutes: %d";
    private static final String MOST_FREQUENT_TERMS_MSG = "Most frequent terms in the last %d minutes are: %s";
    private static final String MOST_ACTIVE_TWEEPS_MSG = "Most active Tweeps in the last %d minutes are: %s";

    private static final int MILLIS = 1000;
    private static final int SECONDS = 60;
    private static final int MINUTES_IN_MILLIS = SECONDS * MILLIS;

    private Store store;

    /*
     * ====================================================================================================
     * CONSTRUCTORS
     * ====================================================================================================
     */
    public Monitoring(Store store)
    {
        this.store = store;
    }

    /*
     * ====================================================================================================
     * PUBLIC
     * ====================================================================================================
     */
    @Override
    public void run() {

        while(true)
        {
            try
            {
                extractTotalDocStats();
                extractDetailedStats();
            }
            catch(Exception e2) // The index is not ready yet.
            {
                sleep(10000);
            }
            finally
            {
                sleep(5000); // 5 secs as per requirement.
            }
        }
    }

    /*
     * ====================================================================================================
     * PRIVATE
     * ====================================================================================================
     */
    private void extractTotalDocStats()
    {
        Collection<Object> total = (Collection<Object>) store.retrieve(new AllTweetsQuery());
        print(TOTAL_NUMBER_OF_DOCUMENTS + total.size());
    }

    private void extractDetailedStats()
    {
        Integer[] intervals = new Integer[]{1,5,15};

        for (Integer minutes : intervals)
        {
            // get start date
            Date start = new Date(System.currentTimeMillis() - minutes * MINUTES_IN_MILLIS);

            // Query Lucene Index and Retrieve Statistics
            LuceneQuery<Statistics> query = new StatsDateRangeQuery(start, new Date());
            Statistics stats15 = (Statistics) store.retrieve(query);

            // Print results to screen
            print(message(TOTAL_NUMBER_OF_TWEETS_MSG, minutes, stats15.getTotalTweets()));
            print(message(MOST_FREQUENT_TERMS_MSG, minutes, StringUtils.join(stats15.getMostFrequentTerms(10))));
            print(message(MOST_ACTIVE_TWEEPS_MSG, minutes, StringUtils.join(stats15.getMostFrequentUsernames(10))));
            print(SEPARATOR);
        }
    }

    private String message(String template, int minutes, Object result)
    {
        return String.format(template, minutes, result);
    }

    private void print(String message)
    {
        System.out.println(message);
    }

    private void sleep(int millis)
    {
        try
        {
            Thread.sleep(millis);
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
}
