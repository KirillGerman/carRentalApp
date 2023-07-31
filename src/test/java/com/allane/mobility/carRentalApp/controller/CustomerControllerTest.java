package com.allane.mobility.carRentalApp.controller;

import com.allane.mobility.carRentalApp.dto.CustomerDto;
import com.allane.mobility.carRentalApp.exception.CustomerNotFoundException;
import com.allane.mobility.carRentalApp.service.CustomerService;
import com.allane.mobility.carRentalApp.utils.DtoUtilsClass;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private CustomerService customerService;

    @Test
    void shouldReturn201AndCustomerDtoWhenCustomerCreated() throws Exception {

        var customerDto = DtoUtilsClass.createCustomerDto();
        customerDto.setId(1L);
        Mockito.when(customerService.createCustomer(Mockito.any())).thenReturn(customerDto);

        var customerJson = objectMapper.writeValueAsString(customerDto);

        mockMvc.perform(post("/api/v1/customers/")
                        .contentType("application/json")
                        .content(customerJson))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.first_name").value(customerDto.getFirstName()))
                .andExpect(jsonPath("$.last_name").value(customerDto.getLastName()))
                .andExpect(jsonPath("$.date_of_birth").value(customerDto.getDateOfBirth().toString()))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn422WhenCustomerIsUnProcessable() throws Exception {
        var customer = CustomerDto.builder()
                .firstName("abc")
                .build();

        var customerJson = objectMapper.writeValueAsString(customer);

        mockMvc.perform(post("/api/v1/customers/")
                        .contentType("application/json")
                        .content(customerJson))
                .andExpect(status().isUnprocessableEntity());

        customer = CustomerDto.builder()
                .firstName("Firstname123")
                .lastName("Lastname123")
                .dateOfBirth(LocalDate.of(2020,1,1))
                .build();

        customerJson = objectMapper.writeValueAsString(customer);

        mockMvc.perform(post("/api/v1/customers/")
                        .contentType("application/json")
                        .content(customerJson))
                .andExpect(status().isUnprocessableEntity());

    }


    @Test
    void shouldReturn204WhenCustomerDeleted() throws Exception {
        doNothing().when(customerService).deleteCustomer(Mockito.any());

        mockMvc.perform(delete("/api/v1/customers/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenNonExistingCustomerUpdated() throws Exception {
        doThrow(new CustomerNotFoundException("1")).when(customerService).updateCustomer(Mockito.any() ,Mockito.any());

        var customer = CustomerDto.builder()
                .firstName("Firstname")
                .lastName("Lastname")
                .build();

        var customerJson = objectMapper.writeValueAsString(customer);
        mockMvc.perform(put("/api/v1/customers/100")
                        .contentType("application/json")
                        .content(customerJson))
                .andExpect(status().isNotFound());
    }

}