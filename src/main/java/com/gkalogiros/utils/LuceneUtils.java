package com.gkalogiros.utils;

import com.gkalogiros.lucene.fields.LuceneField;
import com.gkalogiros.main.App;
import com.gkalogiros.models.Statistics;
import com.gkalogiros.models.Tweet;
import com.google.common.collect.*;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Terms;
import org.apache.lucene.index.TermsEnum;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.util.BytesRef;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.gkalogiros.lucene.fields.LuceneField.*;

public class LuceneUtils {

    /**
     * This method converts a set of Lucene Docs to a list of Tweet messages.
     *
     * @param topDocs the docs returned from a Lucene Query
     * @param indexSearcher the indexSearch used to search the index.
     * @return Query results as a list of Tweets.
     *
     * @throws IOException
     */
    public static List<Tweet> convertDocsToList(
            final TopDocs topDocs, final IndexSearcher indexSearcher) throws IOException
    {
        List<Tweet> tweets = new ArrayList<Tweet>(topDocs.totalHits);
        for (ScoreDoc scoreDoc : topDocs.scoreDocs)
        {
            final Document doc = indexSearcher.doc(scoreDoc.doc);
            final Tweet tweet = new Tweet(
                    doc.get(USER.NAME), null,
                    doc.get(MESSAGE.NAME));

            tweets.add(tweet);
        }
        return tweets;
    }

    public static Statistics extractStatistics(
            final TopDocs topDocs, final IndexSearcher indexSearcher,
            final IndexReader indexReader) throws IOException {

        Multiset<String> terms = LinkedHashMultiset.create();
        Multiset<String> users = LinkedHashMultiset.create();

        int totalTweets = topDocs.totalHits;

        for (ScoreDoc scoreDoc : topDocs.scoreDocs)
        {
            String user = extractUserName(indexSearcher, scoreDoc);
            users.add(user);

            final TermsEnum words = extractTermsEnum(indexReader, scoreDoc);
            BytesRef byteRef = null;
            while ((byteRef = words.next()) != null)
            {
                String term = new String(byteRef.bytes, byteRef.offset, byteRef.length);
                // Filter words for unwanted terms e.g urls or the initial search term.
                if (term.equalsIgnoreCase(App.TERM)) continue;
                terms.add(term.toLowerCase());
            }
        }

        return new Statistics(
                Multisets.copyHighestCountFirst(users).entrySet(),
                Multisets.copyHighestCountFirst(terms).entrySet(),
                totalTweets
        );
    }

    private static String extractUserName(
            final IndexSearcher indexSearcher, final ScoreDoc scoreDoc) throws IOException {
        final Document document = indexSearcher.doc(scoreDoc.doc);
        return document.get(USER.NAME);
    }

    private static TermsEnum extractTermsEnum(
            final IndexReader indexReader, final ScoreDoc scoreDoc) throws IOException
    {
        int docID = scoreDoc.doc;
        Terms terms = indexReader.getTermVector(docID, LuceneField.MESSAGE.NAME);
        return terms.iterator(null);
    }
}
