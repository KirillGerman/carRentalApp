package com.allane.mobility.carRentalApp.exception;

public class CustomerNotFoundException extends EntityNotFoundException{

    public CustomerNotFoundException(String message) {
        super("Customer with id " + message + " not found");
    }
}
