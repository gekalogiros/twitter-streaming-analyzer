package com.gkalogiros.store;

import com.gkalogiros.lucene.fields.LuceneField;
import com.gkalogiros.lucene.LuceneQuery;
import com.gkalogiros.models.Tweet;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;

import java.io.IOException;
import java.util.Collections;

import static com.gkalogiros.lucene.fields.LuceneField.*;

/**
 * This class is an implementation of {@link com.gkalogiros.store.Store}
 *
 * This class represents an In Memory Lucene index that can be used
 * for storing tweet messages as well as statistics about them.
 */
public class InMemoryStoreImpl implements Store<Tweet> {

    /*
     * ====================================================================================================
     * INSTANCE VARIABLES
     * ====================================================================================================
     */

    //private static InMemoryStoreImpl instance;
    private StandardAnalyzer analyzer;

    /* The class representing my in-memory index. */
    private Directory idx;

    /*
     * ====================================================================================================
     * CONSTRUCTORS
     * ====================================================================================================
     */

    public InMemoryStoreImpl()
    {
        this.analyzer = new StandardAnalyzer();
        this.idx = new RAMDirectory();
    }

    /*
     * ====================================================================================================
     * PUBLIC
     * ====================================================================================================
     */


    /**
     * This method indexes a tweet message in the in memory store.
     *
     * @param tweet a simple tweet message containing the author, content and date.
     */
    @Override
    public void put(Tweet tweet) {
        try
        {
            IndexWriterConfig conf = new IndexWriterConfig(analyzer);
            IndexWriter writer = new IndexWriter(idx, conf);
            writer.addDocument(createDocument(tweet));
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Executes a Query and returns back the corresponding data from the Search index.
     * @param query This must be a class extending {@link com.gkalogiros.lucene.LuceneQuery}.
     *
     * @return
     */
    @Override
    public Object retrieve(LuceneQuery query){
        try
        {
            query.onIndex(idx);
            return query.execute();
        }
        catch (Exception e)
        {
            return Collections.emptyList();
        }
    }

    /*
     * ====================================================================================================
     * PRIVATE
     * ====================================================================================================
     */

    private Document createDocument(Tweet tweet) {
        return documentWithFields(new Document(), tweet);
    }

    private Document documentWithFields(Document doc, final Tweet tweet){

        final String hash = String.valueOf(tweet.hashCode()); // id
        final long date = tweet.getDate().getTime(); // millis representing the date

        doc.add(createStringField(ID, hash));
        doc.add(createStringField(USER, tweet.getUsername()));
        doc.add(createTextField(MESSAGE, tweet.getContent()));
        doc.add(createDateField(DATE, date));

        return doc;
    }

    private Field createStringField(final LuceneField field, final String value)
    {
        return new StringField(field.NAME, value, Field.Store.YES);
    }

    private Field createTextField(final LuceneField field, final String value)
    {
        return new Field(field.NAME, value, createTermVectorFieldType());
    }

    private FieldType createTermVectorFieldType()
    {
        FieldType type = new FieldType();
        type.setIndexOptions(IndexOptions.DOCS_AND_FREQS_AND_POSITIONS_AND_OFFSETS);
        type.setStored(true);
        type.setStoreTermVectors(true);
        return type;
    }

    private Field createDateField(final LuceneField field, final long date)
    {
        return new LongField(field.NAME, date, Field.Store.YES);
    }

    /*
     * ====================================================================================================
     * GETTERS / SETTERS
     * ====================================================================================================
     */

    public StandardAnalyzer getAnalyzer() {
        return analyzer;
    }

    public void setAnalyzer(StandardAnalyzer analyzer) {
        this.analyzer = analyzer;
    }

    public Directory getIdx() {
        return idx;
    }

    public void setIdx(Directory idx) {
        this.idx = idx;
    }
}
