package com.allane.mobility.carRentalApp.controller;

import com.allane.mobility.carRentalApp.dto.VehicleDto;
import com.allane.mobility.carRentalApp.exception.VehicleNotFoundException;
import com.allane.mobility.carRentalApp.service.VehicleService;
import com.allane.mobility.carRentalApp.utils.DtoUtilsClass;
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
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@WebMvcTest(controllers = VehicleController.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private VehicleService vehicleService;


    @Test
    void shouldReturn201AndVehicleDtoWhenVehicleCreated() throws Exception {

        var vehicleDto = DtoUtilsClass.createVehicleDto();
        vehicleDto.setId(1L);
        Mockito.when(vehicleService.createVehicle(Mockito.any())).thenReturn(vehicleDto);

        var vehicleJson = objectMapper.writeValueAsString(vehicleDto);

        mockMvc.perform(post("/api/v1/vehicles/")
                        .contentType("application/json")
                        .content(vehicleJson))
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.brand").value(vehicleDto.getBrand()))
                .andExpect(jsonPath("$.model").value(vehicleDto.getModel()))
                .andExpect(jsonPath("$.model_year").value(vehicleDto.getModelYear().toString()))
                .andExpect(jsonPath("$.vin").value(vehicleDto.getVin()))
                .andExpect(jsonPath("$.price").value(vehicleDto.getPrice()))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldReturn422WhenVehicleUnProcessable() throws Exception {
        var vehicle = VehicleDto.builder()
                .brand("BMW")
                .model("e46")
                .vin("123")
                .build();

        var vehicleJson = objectMapper.writeValueAsString(vehicle);

        mockMvc.perform(post("/api/v1/vehicles/")
                        .contentType("application/json")
                        .content(vehicleJson))
                .andExpect(status().isUnprocessableEntity());
    }


    @Test
    void shouldReturn204WhenVehicleDeleted() throws Exception {
        doNothing().when(vehicleService).deleteVehicle(Mockito.any());

        mockMvc.perform(delete("/api/v1/vehicles/1")
                        .contentType("application/json"))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturn404WhenNonExistingVehicleUpdated() throws Exception {
        doThrow(new VehicleNotFoundException("1")).when(vehicleService).updateVehicle(Mockito.any() ,Mockito.any());

        var vehicle = VehicleDto.builder()
                .brand("Audi")
                .model("A4")
                .price(BigDecimal.TEN)
                .build();

        var vehicleJson = objectMapper.writeValueAsString(vehicle);
        mockMvc.perform(put("/api/v1/vehicles/100")
                        .contentType("application/json")
                        .content(vehicleJson))
                .andExpect(status().isNotFound());
    }

}