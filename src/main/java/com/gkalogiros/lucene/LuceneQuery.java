package com.gkalogiros.lucene;

import com.gkalogiros.exceptions.IndexHasNotBeenSetException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;

import java.io.IOException;

/**
 * This is a top level class representing a Lucene Query.
 * You can create custom Lucene Queries by inheriting the current
 * class.
 * @param <T>
 */
public abstract class LuceneQuery<T> {

    /*
     * ====================================================================================================
     * INSTANCE VARIABLES
     * ====================================================================================================
     */

    private IndexSearcher indexSearcher;
    private IndexReader indexReader;

    /* This class represents the In-Memory Lucene Index */
    private Directory index;

    /*
     * ====================================================================================================
     * ABSTRACT
     * ====================================================================================================
     */

    /**
     * Used for executing the current Query
     * @return T
     */
    public abstract T execute();

    /*
     * ====================================================================================================
     * PUBLIC
     * ====================================================================================================
     */

    /**
     * Validates whether the index is blank or not.
     */
    public void validateIndex()
    {
        if (null == indexReader) throw new IndexHasNotBeenSetException();
    }

    /**
     * This method is used for Lazy initialization of the Lucene index.
     * This method must be called before {@link LuceneQuery#execute()}.
     *
     * @param index a Lucene index.
     */
    public void onIndex(final Directory index) {
        try
        {
            this.index=index;
            indexReader = DirectoryReader.open(this.index);
            indexSearcher = new IndexSearcher(indexReader);
        }
        catch (IOException e)
        {
            System.out.println("Index is not ready yet.");
        }
    }

    /*
     * ====================================================================================================
     * GETTERS / SETTERS
     * ====================================================================================================
     */
    public IndexSearcher getIndexSearcher() {
        return indexSearcher;
    }

    public void setIndexSearcher(IndexSearcher indexSearcher) {
        this.indexSearcher = indexSearcher;
    }

    public IndexReader getIndexReader() {
        return indexReader;
    }

    public void setIndexReader(IndexReader indexReader) {
        this.indexReader = indexReader;
    }

    public Directory getIndex() {
        return index;
    }

    public void setIndex(Directory index) {
        this.index = index;
    }
}
