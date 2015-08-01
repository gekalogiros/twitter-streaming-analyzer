package com.gkalogiros.lucene.fields;

public enum LuceneField {

    ID("id"),
    USER("user"),
    MESSAGE("message"),
    DATE("date"),
    SENTIMENT("sentiment");

    public String NAME;

    LuceneField(final String type)
    {
        NAME = type;
    }

    public String toString()
    {
        return NAME;
    }

}
