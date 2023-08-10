package com.paweu.autofleet.error.exceptions;

public class InvoiceNotFoundException extends ResourceNotFoundException{
    public InvoiceNotFoundException(){
        super("Invoice Not Found");
    }

}
