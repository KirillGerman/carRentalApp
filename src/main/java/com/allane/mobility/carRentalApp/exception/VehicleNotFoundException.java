package com.allane.mobility.carRentalApp.exception;

public class VehicleNotFoundException extends EntityNotFoundException {

    public VehicleNotFoundException(String message) {
        super("Vehicle with id " + message + " not found");
    }
}
