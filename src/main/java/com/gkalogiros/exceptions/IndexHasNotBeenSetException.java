package com.gkalogiros.exceptions;

/**
 * This is thrown when someone tries to execute a query against
 * a blank index.
 */
public class IndexHasNotBeenSetException extends RuntimeException{

    public IndexHasNotBeenSetException()
    {
        super("Index has not been set! Set Index before executing a Query!");
    }
}
