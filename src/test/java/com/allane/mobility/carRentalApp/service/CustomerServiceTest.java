package com.allane.mobility.carRentalApp.service;

import com.allane.mobility.carRentalApp.dto.CustomerDto;
import com.allane.mobility.carRentalApp.utils.MySqlExtension;
import com.allane.mobility.carRentalApp.utils.DtoUtilsClass;
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
class CustomerServiceTest {

    @Autowired
    CustomerService customerService;

    @Test
    void shouldGetCustomerById(){
        var customer = customerService.getCustomer(1L);
        Assertions.assertNotNull(customer);
    }

    @Test
    void shouldGetAllCustomers(){
        var customers = customerService.getCustomers();
        Assertions.assertNotNull(customers);
    }

    @Test
    void shouldCreateCustomer(){
        var customer = customerService.createCustomer(DtoUtilsClass.createCustomerDto());
        customer =  customerService.getCustomer(customer.getId());
        Assertions.assertNotNull(customer);
    }

    @Test
    void shouldUpdateCustomer(){

        var newCustomer = CustomerDto.builder()
                .firstName("FirstnameAbc")
                .build();

        var customer =  customerService.updateCustomer(1L, newCustomer);
        Assertions.assertEquals("FirstnameAbc", customer.getFirstName());
    }

}