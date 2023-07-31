package com.allane.mobility.carRentalApp.dto;

import com.allane.mobility.carRentalApp.model.ContractStatus;
import com.allane.mobility.carRentalApp.utils.OnCreate;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ContractDto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotNull(groups = OnCreate.class, message = "monthlyRate not set")
    @DecimalMin(value = "0.0", inclusive = true, message = "monthlyRate below zero")
    @JsonProperty("monthly_rate")
    private BigDecimal monthlyRate;

    @Schema(hidden = true)
    @JsonProperty(value = "customer", access = JsonProperty.Access.READ_ONLY)
    private CustomerDto customer;

    @Schema(hidden = true)
    @JsonProperty(value = "vehicle", access = JsonProperty.Access.READ_ONLY)
    private VehicleDto vehicle;

    @Schema(hidden = true)
    @JsonProperty(value = "status", access = JsonProperty.Access.READ_ONLY)
    ContractStatus status;

    public ContractDto(Long id) {
        this.id = id;
    }

    public ContractDto(BigDecimal monthlyRate) {
        this.monthlyRate = monthlyRate;
    }
}
