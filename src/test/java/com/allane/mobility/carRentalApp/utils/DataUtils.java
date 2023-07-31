package com.allane.mobility.carRentalApp.utils;

import com.allane.mobility.carRentalApp.dto.ContractDto;
import com.allane.mobility.carRentalApp.repo.ContractRateHistoryRepo;
import com.allane.mobility.carRentalApp.service.ContractService;
import com.allane.mobility.carRentalApp.service.CustomerService;
import com.allane.mobility.carRentalApp.service.VehicleService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

//@Disabled
@SpringBootTest
public class DataUtils {

    @Autowired
    ContractService contractService;
    @Autowired
    CustomerService customerService;
    @Autowired
    VehicleService vehicleService;
    @Autowired
    ContractRateHistoryRepo contractRateHistoryRepo;

    @Test
    void createData(){
        for (int i = 0; i < 10; i++) {
            var customer = customerService.createCustomer(DtoUtilsClass.createCustomerDto());
            for (int j = 0; j < 2; j++) {
                var vehicle =  vehicleService.createVehicle(DtoUtilsClass.createVehicleDto());
                contractService.createContract(customer.getId(), vehicle.getId(), new ContractDto(BigDecimal.TEN));
            }
        }
    }

}
