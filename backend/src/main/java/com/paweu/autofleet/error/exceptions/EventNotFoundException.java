package com.paweu.autofleet.error.exceptions;

public class EventNotFoundException extends ResourceNotFoundException{
    public EventNotFoundException(){
        super("Event Not Fount");
    }
}
