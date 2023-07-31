package com.allane.mobility.carRentalApp.controller;

import com.allane.mobility.carRentalApp.dto.ContractDto;
import com.allane.mobility.carRentalApp.model.ContractStatus;
import com.allane.mobility.carRentalApp.service.ContractService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/contracts")
@RequiredArgsConstructor
@Validated
public class ContractController {

    private final ContractService contractService;

    @GetMapping("/")
    public List<ContractDto> getContact(@RequestParam(name = "contactId", required = false) Long contactId,
                                  @RequestParam(name = "customerId", required = false) Long customerId,
                                  @RequestParam(name = "vehicleId", required = false) Long vehicleId,
                                  @RequestParam(name = "status", required = false) ContractStatus status) {
        return contractService.getContacts(contactId, customerId, vehicleId, status);
    }

    @GetMapping("/{contactId}")
    public ContractDto getContact(@PathVariable Long contactId) {
        return contractService.getContact(contactId);
    }

    @GetMapping("/customerId/{customerId}/vehicle/{vehicleId}")
    public ContractDto getContactByCustomerAndVehicle(@PathVariable Long customerId, @PathVariable Long vehicleId){
        return contractService.getContactByCustomerIdAndVehicleId(customerId, vehicleId);
    }

    @PostMapping("/customerId/{customerId}/vehicle/{vehicleId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class)
    public ContractDto createContract(@PathVariable Long customerId,
                                      @PathVariable Long vehicleId,
                                      @Valid @RequestBody ContractDto contact){
        return contractService.createContract(customerId, vehicleId, contact);
    }

    @PutMapping("/{contactId}")
    @Validated(OnUpdate.class)
    public ContractDto updateContract(@PathVariable Long contactId,
                                      @Valid @RequestBody ContractDto contact){
        return contractService.updateContract(contactId, contact);
    }

    @DeleteMapping("/{contractId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContract(@PathVariable Long contractId){
        contractService.deleteContract(contractId);
    }

}

