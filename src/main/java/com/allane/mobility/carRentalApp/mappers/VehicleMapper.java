package com.allane.mobility.carRentalApp.mappers;

import com.allane.mobility.carRentalApp.dto.VehicleDto;
import com.allane.mobility.carRentalApp.model.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Named("VehicleMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ContractMapper.class)
public abstract class VehicleMapper {

    public abstract Vehicle toVehicle(VehicleDto vehicle);

    @Mapping(target = "contract", source = "contract", qualifiedByName = "toContactDto")
    public abstract VehicleDto toVehicleDto(Vehicle vehicle);

    public abstract List<VehicleDto> toVehicleDto(List<Vehicle> vehicles);

}
