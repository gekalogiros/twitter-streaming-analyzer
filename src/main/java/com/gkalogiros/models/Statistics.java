package com.gkalogiros.models;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import java.util.Collection;

import static com.google.common.collect.Multiset.Entry;

/**
 * This is a simple model class representing the statistics
 * that need to be printed out when running the current program.
 */
public class Statistics {

    private Collection<Entry<String>> terms, usernames;
    private Integer totalTweets;
    private double sentiment;

    public Statistics()
    {
        this.usernames = Lists.newArrayList();
        this.terms = Lists.newArrayList();
        this.totalTweets = 0;
        this.sentiment = 0;
    }

    public Statistics(
            final Collection<Entry<String>> usernames,
            final Collection<Entry<String>> terms,
            final int tweets,
            final double sentiment)
    {
        this.usernames = usernames;
        this.terms = terms;
        this.totalTweets = tweets;
        this.sentiment = sentiment;
    }

    public Collection<Entry<String>> getTerms() {
        return this.terms;
    }

    public Collection<String> getMostFrequentTerms(int size) {
        Iterable itr = Iterables.limit(terms, size);
        return ImmutableSet.copyOf(itr);
    }

    public void setTerms(Collection<Entry<String>> terms) {
        this.terms = terms;
    }

    public Collection<Entry<String>> getUsernames() {
        return usernames;
    }

    public Collection<Entry<String>> getMostFrequentUsernames(int size) {
        return ImmutableSet.copyOf(Iterables.limit(usernames, size));
    }

    public void setUsernames(Collection<Entry<String>> usernames) {
        this.usernames = usernames;
    }

    public Integer getTotalTweets() {
        return totalTweets;
    }

    public void setTotalTweets(Integer tweets) {
        this.totalTweets = tweets;
    }

    public double getSentiment() {
        return sentiment;
    }

    public void setSentiment(double sentiment) {
        this.sentiment = sentiment;
    }
}
