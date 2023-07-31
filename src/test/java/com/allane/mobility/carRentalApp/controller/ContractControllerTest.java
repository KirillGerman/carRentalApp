package com.allane.mobility.carRentalApp.controller;

import com.allane.mobility.carRentalApp.dto.ContractDto;
import com.allane.mobility.carRentalApp.service.ContractService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = ContractController.class)
class ContractControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private ContractService contractService;

    @Test
    void shouldReturn201AndContractDtoWhenContractCreated() throws Exception {

        var contractDto = ContractDto.builder()
                .id(1L)
                .monthlyRate(BigDecimal.TEN)
                .build();

        Mockito.when(contractService.createContract(Mockito.any(), Mockito.any(), Mockito.any()))
                .thenReturn(contractDto);

        var contractJson = objectMapper.writeValueAsString(contractDto);

        mockMvc.perform(post("/api/v1/contracts/customerId/1/vehicle/1")
                        .contentType("application/json")
                        .content(contractJson))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(status().isCreated());

    }

    @Test
    void shouldReturn422WhenContractIsUnProcessable() throws Exception{

        var contractDto = ContractDto.builder()
                .id(1L)
                .monthlyRate(BigDecimal.TEN.multiply(new BigDecimal(-1)))
                .build();

        var contractJson = objectMapper.writeValueAsString(contractDto);

        mockMvc.perform(post("/api/v1/contracts/customerId/1/vehicle/1")
                        .contentType("application/json")
                        .content(contractJson))
                .andExpect(status().isUnprocessableEntity());

    }

    @Test
    void shouldReturn200andContractDtoWhenGetContract() throws Exception{
        mockMvc.perform(get("/api/v1/contracts/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/api/v1/contracts/customerId/1/vehicle/1")
                        .contentType("application/json"))
                .andExpect(status().isOk());
    }


    @Test
    void shouldReturn204WhenContractDeleted() throws Exception {
        doNothing().when(contractService).deleteContract(Mockito.any());
        mockMvc.perform(delete("/api/v1/contracts/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

}