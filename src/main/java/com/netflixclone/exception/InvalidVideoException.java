package com.netflixclone.exception;

public class InvalidVideoException extends RuntimeException{

    public InvalidVideoException(final String message){
        super(message);
    }
}
