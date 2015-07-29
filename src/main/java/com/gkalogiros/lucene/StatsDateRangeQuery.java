package com.gkalogiros.lucene;

import com.gkalogiros.models.Statistics;
import com.gkalogiros.utils.LuceneUtils;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;
import java.util.Date;

import static com.gkalogiros.lucene.fields.LuceneField.DATE;

/**
 * This class inherits from {@link com.gkalogiros.lucene.LuceneQuery}.
 * This class represents a Lucene query that can be fed to
 * {@link com.gkalogiros.store.Store#retrieve(LuceneQuery)}.
 */
public class StatsDateRangeQuery extends LuceneQuery<Statistics> {

    /*
     * ====================================================================================================
     * INSTANCE VARIABLES
     * ====================================================================================================
     */

    private Date from, to;

    /*
     * ====================================================================================================
     * CONSTRUCTORS
     * ====================================================================================================
     */

    public StatsDateRangeQuery(final Date from, final Date to)
    {
        super();
        this.from = from;
        this.to   = to;
    }

    /*
     * ====================================================================================================
     * PUBLIC
     * ====================================================================================================
     */

    /**
     * It Executes a Lucene Date Range query. It analyzes the
     * result set and extracts the following information:
     * 1) The active twitter users in the given date range
     * 2) The total terms appearing in tweets published at some
     *    point within the given date range.
     * 3) The Total number of tweets that have been published
     *    within the given date range.
     *
     * The above statistics are wrapped in an instance of the
     *  {@link com.gkalogiros.models.Statistics} object.
     *
     * @return Statistics The said statistics.
     */
    @Override
    public Statistics execute(){
        try
        {
            validateIndex();
            final Query query = createDateRangeQuery(from, to);
            return search(query);
        }
        catch(IOException e)
        {
            return new Statistics();
        }
    }

    /*
     * ====================================================================================================
     * PRIVATE
     * ====================================================================================================
     */

    private Statistics search(org.apache.lucene.search.Query query) throws IOException
    {
        final TopDocs topDocs = getIndexSearcher().search(query, getIndexReader().numDocs());
        return LuceneUtils.extractStatistics(topDocs,getIndexSearcher(), getIndexReader());
    }

    private NumericRangeQuery<Long> createDateRangeQuery(final Date dateFrom,
                                                         final Date dateTo)
    {
        return NumericRangeQuery.newLongRange(
                DATE.NAME, dateFrom.getTime(), dateTo.getTime(), true, true);
    }

    /*
     * ====================================================================================================
     * GETTERS / SETTERS
     * ====================================================================================================
     */

    public Date getFrom() {
        return from;
    }

    public void setFrom(Date from) {
        this.from = from;
    }

    public Date getTo() {
        return to;
    }

    public void setTo(Date to) {
        this.to = to;
    }
}
