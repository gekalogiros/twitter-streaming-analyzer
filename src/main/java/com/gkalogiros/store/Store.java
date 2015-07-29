package com.gkalogiros.store;

import com.gkalogiros.lucene.LuceneQuery;

public interface Store<T> {
    public void put(T t);
    public Object retrieve(LuceneQuery query);

}
