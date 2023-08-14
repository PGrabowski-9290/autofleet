package com.paweu.autofleet.error.exceptions;

public class UserAlreadyExistException extends ResourceAlreadyExistException{
    public UserAlreadyExistException(){
        super("User Already Exist");
    }
}
