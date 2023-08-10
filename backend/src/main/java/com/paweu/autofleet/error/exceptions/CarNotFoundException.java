package com.paweu.autofleet.error.exceptions;

public class CarNotFoundException extends ResourceNotFoundException{
    public CarNotFoundException(){
        super("Car Not Found");
    }
}
