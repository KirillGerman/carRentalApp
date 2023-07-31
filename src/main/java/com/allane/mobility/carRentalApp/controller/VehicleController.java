package com.allane.mobility.carRentalApp.controller;

import com.allane.mobility.carRentalApp.dto.VehicleDto;
import com.allane.mobility.carRentalApp.service.VehicleService;
import com.allane.mobility.carRentalApp.utils.OnCreate;
import com.allane.mobility.carRentalApp.utils.OnUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/vehicles")
@RequiredArgsConstructor
@Validated
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping("/{vehicleId}")
    public VehicleDto getVehicle(@PathVariable Long vehicleId) {
        return vehicleService.getVehicle(vehicleId);
    }

    @GetMapping("/")
    public List<VehicleDto> getVehicles() {
        return vehicleService.getVehicles();
    }

    @DeleteMapping("/{vehicleId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteVehicle(@PathVariable Long vehicleId) {
        vehicleService.deleteVehicle(vehicleId);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public VehicleDto createVehicle(@Valid @RequestBody VehicleDto vehicleDto){
        return vehicleService.createVehicle(vehicleDto);
    }

    @PutMapping("/{vehicleId}")
    public VehicleDto updateVehicle(@PathVariable Long vehicleId, @Valid @RequestBody VehicleDto vehicleDto){
        return vehicleService.updateVehicle(vehicleId, vehicleDto);
    }
}
