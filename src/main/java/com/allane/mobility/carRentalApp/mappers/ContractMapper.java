package com.allane.mobility.carRentalApp.mappers;

import com.allane.mobility.carRentalApp.dto.ContractDto;
import com.allane.mobility.carRentalApp.model.Contract;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Named("ContractMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ContractMapper {

    @Mapping(target = "currentMonthlyRate", source = "monthlyRate")
    public abstract Contract toContact(ContractDto contract);

    @Mappings({
            @Mapping(target = "customer.contracts", expression = "java(null)"),
            @Mapping(target = "vehicle.contract", expression = "java(null)"),
            @Mapping(target = "monthlyRate", source = "currentMonthlyRate")
    })
    @Named("toContactDto")
    public abstract ContractDto toContactDto(Contract contract);

    @Mappings({
            @Mapping(target = "customer.contracts", expression = "java(null)"),
            @Mapping(target = "vehicle.contract", expression = "java(null)"),
            @Mapping(target = "monthlyRate", source = "currentMonthlyRate")
    })
    public abstract ContractDto toDto(Contract contract);

    @Named("toContactDto")
    public abstract List<ContractDto> toContactDto(List<Contract> contracts);

}
