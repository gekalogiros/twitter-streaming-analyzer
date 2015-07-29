package com.gkalogiros.lucene;

import com.gkalogiros.models.Tweet;
import com.gkalogiros.utils.LuceneUtils;
import com.google.common.collect.Lists;
import org.apache.lucene.search.*;

import java.io.IOException;
import java.util.List;

/**
 * This class inherits from {@link com.gkalogiros.lucene.LuceneQuery}.
 * An instance of this class can be used as an input to
 * {@link com.gkalogiros.store.Store#retrieve(LuceneQuery)}.
 */
public class AllTweetsQuery extends LuceneQuery<List<Tweet>> {

    public AllTweetsQuery()
    {
        super();
    }

    /**
     * Executes a Match All Lucene Query and it returns all the tweets
     * found in the index. Use it cautiously.
     *
     * @return A list with all the Tweets stored in the system.
     */
    @Override
    public List<Tweet> execute(){
        try
        {
            validateIndex();
            final Query query = new MatchAllDocsQuery();
            return search(query);
        }
        catch(IOException e)
        {
            return Lists.newArrayList();
        }
    }

    private List<Tweet> search(Query query) throws IOException {
        final TopDocs topDocs = getIndexSearcher().search(query, getIndexReader().numDocs());
        return LuceneUtils.convertDocsToList(topDocs, getIndexSearcher());
    }

}
