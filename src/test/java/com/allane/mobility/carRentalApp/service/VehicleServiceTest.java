package com.allane.mobility.carRentalApp.service;

import com.allane.mobility.carRentalApp.dto.VehicleDto;
import com.allane.mobility.carRentalApp.utils.MySqlExtension;
import com.allane.mobility.carRentalApp.utils.DtoUtilsClass;
import de.kyrychenko.utils.vin.VinGeneratorUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;


import static org.springframework.test.context.jdbc.Sql.ExecutionPhase.AFTER_TEST_METHOD;

@SpringBootTest
//@ExtendWith(MySqlExtension.class)
@Sql({
        "classpath:service/customers.sql",
        "classpath:service/vehicles.sql",
        "classpath:service/contracts.sql",
        "classpath:service/contract_rate_history.sql"
})
@Sql(value = "classpath:service/clear_db.sql", executionPhase = AFTER_TEST_METHOD)
class VehicleServiceTest {

    @Autowired
    VehicleService vehicleService;

    @Test
    void shouldGetVehicleById(){
        var vehicle = vehicleService.getVehicle(1L);
        Assertions.assertNotNull(vehicle);
    }

    @Test
    void shouldGetAllVehicles(){
        var vehicles = vehicleService.getVehicles();
        Assertions.assertNotNull(vehicles);
    }

    @Test
    void shouldCreateVehicle(){
        var vehicle = vehicleService.createVehicle(DtoUtilsClass.createVehicleDto());
        vehicle =  vehicleService.getVehicle(vehicle.getId());
        Assertions.assertNotNull(vehicle);
    }

    @Test
    void shouldUpdateVehicle(){

        String vin = VinGeneratorUtils.getRandomVin();

        var newVehicle = VehicleDto.builder()
                .vin(vin)
                .build();

        var vehicle =  vehicleService.updateVehicle(1L, newVehicle);
        Assertions.assertEquals(vin, vehicle.getVin());
    }


}


