package com.netflixclone.exception;

public class InvalidProfileException extends RuntimeException{

    public InvalidProfileException(final String message){
    super(message);
}
}