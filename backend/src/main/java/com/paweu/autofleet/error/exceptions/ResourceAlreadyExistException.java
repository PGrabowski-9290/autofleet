package com.paweu.autofleet.error.exceptions;

public class ResourceAlreadyExistException extends RuntimeException{
    public ResourceAlreadyExistException(String msg){
        super(msg);
    }
}
