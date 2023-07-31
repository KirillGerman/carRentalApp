package com.allane.mobility.carRentalApp.dto;

import com.allane.mobility.carRentalApp.utils.OnCreate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;
import de.kyrychenko.utils.vin.constraint.VIN;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class VehicleDto {

    @JsonProperty(value = "id", access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @Schema(example = "Firstname")
    @NotBlank(groups = OnCreate.class, message = "brand not set")
    @Pattern(regexp = "[A-Za-z0-9]*")
    @JsonProperty("brand")
    private String brand;

    @Schema(example = "Lastname")
    @NotBlank(groups = OnCreate.class, message = "model not set")
    @Pattern(regexp = "[A-Za-z0-9]*")
    @JsonProperty("model")
    private String model;

    @Schema(example = "2020")
    @NotNull(groups = OnCreate.class, message = "modelYear not set")
    @JsonFormat(pattern="yyyy")
    @JsonProperty("model_year")
    private LocalDate modelYear;

    @Schema(example = "1FT3F32R98U0WBSDG")
    @VIN(groups = OnCreate.class)
    @JsonProperty("vin")
    private String vin;

    @Schema(example = "10.00")
    @NotNull(groups = OnCreate.class, message = "price not set")
    @DecimalMin(value = "0.0", inclusive = false, message = "wrong price")
    @JsonProperty("price")
    private BigDecimal price;

    @Schema(hidden = true)
    @JsonProperty(value = "contract", access = JsonProperty.Access.READ_ONLY)
    private ContractDto contract;

    public VehicleDto(Long id) {
        this.id = id;
    }

    @JsonSetter("vin")
    public void setSomeValue(String vin) {
        if (vin != null) {
            this.vin = "-";
        }
    }
}
