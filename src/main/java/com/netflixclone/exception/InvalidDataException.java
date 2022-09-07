package com.netflixclone.exception;

public class InvalidDataException extends RuntimeException{

    private String message;

    public InvalidDataException(final String message){
        this.message=message;
    }

    @Override
    public String getMessage(){
        return this.message;
    }
}
