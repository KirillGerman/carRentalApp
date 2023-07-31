package com.allane.mobility.carRentalApp.controller;

import com.allane.mobility.carRentalApp.dto.CustomerDto;
import com.allane.mobility.carRentalApp.service.CustomerService;
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
@RequestMapping("/api/v1/customers")
@RequiredArgsConstructor
@Validated
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{customerId}")
    public CustomerDto getCustomer(@PathVariable Long customerId) {
        return customerService.getCustomer(customerId);
    }

    @GetMapping("/")
    public List<CustomerDto> getCustomers() {
        return customerService.getCustomers();
    }

    @DeleteMapping("/{customerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable Long customerId) {
        customerService.deleteCustomer(customerId);
    }

    @PostMapping("/")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public CustomerDto createCustomer(@Valid @RequestBody CustomerDto customerDto){
        return customerService.createCustomer(customerDto);
    }

    @PutMapping("/{customerId}")
    @Validated(OnUpdate.class)
    public CustomerDto updateCustomer(@PathVariable Long customerId, @Valid @RequestBody CustomerDto CustomerDto){
        return customerService.updateCustomer(customerId, CustomerDto);
    }

}
