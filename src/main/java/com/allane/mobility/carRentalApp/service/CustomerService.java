package com.allane.mobility.carRentalApp.service;

import com.allane.mobility.carRentalApp.dto.CustomerDto;
import com.allane.mobility.carRentalApp.exception.CustomerNotFoundException;
import com.allane.mobility.carRentalApp.mappers.CustomerMapper;
import com.allane.mobility.carRentalApp.repo.CustomerRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepo customerRepo;
    private final CustomerMapper mapper;

    public CustomerDto createCustomer(CustomerDto customerDto){
        var customer = mapper.toCustomer(customerDto);
        return mapper.toCustomerDto(customerRepo.save(customer));
    }

    @Transactional
    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto){
        customerRepo.updateCustomer(customerId, customerDto);
        var customer =  customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        return mapper.toCustomerDto(customer);
    }

    @Transactional
    public CustomerDto getCustomer(Long customerId){
        var customer =  customerRepo.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        return mapper.toCustomerDto(customer);
    }

    @Transactional
    public List<CustomerDto> getCustomers(){
        return mapper.toCustomerDto(customerRepo.findAll());
    }

    public void deleteCustomer(Long customerId){
        customerRepo.findById(customerId).ifPresentOrElse(customerRepo::delete, () -> new CustomerNotFoundException(customerId.toString()));

    }


}
