package com.allane.mobility.carRentalApp.service;

import com.allane.mobility.carRentalApp.dto.VehicleDto;
import com.allane.mobility.carRentalApp.exception.VehicleNotFoundException;
import com.allane.mobility.carRentalApp.mappers.VehicleMapper;
import com.allane.mobility.carRentalApp.repo.VehicleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepo vehicleRepo;
    private final VehicleMapper mapper;

    public VehicleDto createVehicle(VehicleDto vehicleDto){
        var vehicle = mapper.toVehicle(vehicleDto);
        return mapper.toVehicleDto(vehicleRepo.save(vehicle));
    }

    @Transactional
    public VehicleDto updateVehicle(Long vehicleId, VehicleDto vehicleDto){
        vehicleRepo.updateVehicle(vehicleId, vehicleDto);
        var vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId.toString()));
        return mapper.toVehicleDto(vehicle);
    }

    @Transactional
    public VehicleDto getVehicle(Long vehicleId){
        var vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new VehicleNotFoundException(vehicleId.toString()));
        return mapper.toVehicleDto(vehicle);
    }

    @Transactional
    public List<VehicleDto> getVehicles(){
        return mapper.toVehicleDto(vehicleRepo.findAll());
    }

    public void deleteVehicle(Long vehicleId){
        vehicleRepo.findById(vehicleId).ifPresentOrElse(vehicleRepo::delete, () -> new VehicleNotFoundException(vehicleId.toString()));
    }


}
