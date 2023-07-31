package com.allane.mobility.carRentalApp.dto;

import com.allane.mobility.carRentalApp.utils.OnCreate;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerDto {

    @JsonProperty("id")
    private Long id;

    @Schema(example = "Firstname")
    @NotBlank(groups = OnCreate.class, message = "firstName not set")
    @Pattern(regexp = "[A-Z][a-z]*", message = "wrong firstName")
    @JsonProperty("first_name")
    private String firstName;

    @Schema(example = "Lastname")
    @NotBlank(groups = OnCreate.class, message = "lastName not set")
    @Pattern(regexp = "[A-Z][a-z]*", message = "wrong lastName")
    @JsonProperty("last_name")
    private String lastName;

    @Schema(example = "01.01.2020")
    @NotNull(groups = OnCreate.class, message = "dateOfBirth not set")
    @Past(message = "wrong dateOfBirth")
    @JsonProperty("date_of_birth")
    @JsonFormat(pattern="dd.MM.yyyy")
    private LocalDate dateOfBirth;

    @Schema(hidden = true)
    @JsonProperty(value = "contracts", access = JsonProperty.Access.READ_ONLY)
    private List<ContractDto> contracts;

    public CustomerDto(Long id) {
        this.id = id;
    }
}
