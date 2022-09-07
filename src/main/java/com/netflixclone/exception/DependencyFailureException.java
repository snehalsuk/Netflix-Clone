package com.netflixclone.exception;

public class DependencyFailureException extends RuntimeException{

    public DependencyFailureException(Throwable cause){
        super(cause);
    }
}
