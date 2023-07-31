package com.allane.mobility.carRentalApp.mappers;

import com.allane.mobility.carRentalApp.dto.CustomerDto;
import com.allane.mobility.carRentalApp.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Named("CustomerMapper")
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = ContractMapper.class)
public abstract class CustomerMapper {

    public abstract Customer toCustomer(CustomerDto customer);

    @Mapping(target = "contracts", source = "contracts", qualifiedByName = "toContactDto")
    public abstract CustomerDto toCustomerDto(Customer customer);

    public abstract List<CustomerDto> toCustomerDto(List<Customer> customers);

}
