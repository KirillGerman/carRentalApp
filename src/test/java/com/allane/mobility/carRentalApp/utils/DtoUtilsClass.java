package com.allane.mobility.carRentalApp.utils;

import com.allane.mobility.carRentalApp.dto.CustomerDto;
import com.allane.mobility.carRentalApp.dto.VehicleDto;
import de.kyrychenko.utils.vin.VinGeneratorUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Random;

public class DtoUtilsClass {

    private final static Random random = new Random();

    public static CustomerDto createCustomerDto(){
        var customerDto = CustomerDto.builder()
                .dateOfBirth(LocalDate.of(2020,1,1))
                .firstName("Firstname")
                .lastName("Lastname")
                .build();
        return customerDto;
    }

    public static VehicleDto createVehicleDto(){
        var vehicleDto = VehicleDto.builder()
                .brand("brand" + random.nextInt(1000))
                .model("modelA")
                .modelYear(LocalDate.of(2020,1,1))
                .vin(VinGeneratorUtils.getRandomVin())
                .price(BigDecimal.TEN)
                .build();
        return vehicleDto;
    }

}
