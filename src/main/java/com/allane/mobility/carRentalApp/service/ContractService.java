package com.allane.mobility.carRentalApp.service;

import com.allane.mobility.carRentalApp.dto.ContractDto;
import com.allane.mobility.carRentalApp.exception.ContractClosedException;
import com.allane.mobility.carRentalApp.exception.ContractExistsException;
import com.allane.mobility.carRentalApp.exception.ContractNotFoundException;
import com.allane.mobility.carRentalApp.exception.CustomerNotFoundException;
import com.allane.mobility.carRentalApp.exception.VehicleNotFoundException;
import com.allane.mobility.carRentalApp.mappers.ContractMapper;
import com.allane.mobility.carRentalApp.model.Contract;
import com.allane.mobility.carRentalApp.model.ContractRateHistory;
import com.allane.mobility.carRentalApp.model.ContractStatus;
import com.allane.mobility.carRentalApp.repo.ContractRepo;
import com.allane.mobility.carRentalApp.repo.CustomerRepo;
import com.allane.mobility.carRentalApp.repo.VehicleRepo;
import com.allane.mobility.carRentalApp.utils.SpecificationBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ContractService {

    private final ContractRepo contractRepo;
    private final CustomerRepo customerRepo;
    private final VehicleRepo vehicleRepo;
    private final ContractMapper mapper;


    @Transactional
    public ContractDto createContract(Long customerId, Long vehicleId, ContractDto contact){
        contractRepo.findByVehicleId(vehicleId).ifPresent(
               contract -> {throw new ContractExistsException("Contract for vehicle" + vehicleId + "already exists");}
        );
        var customer =  customerRepo.findById(customerId).orElseThrow(() -> new CustomerNotFoundException(customerId.toString()));
        var vehicle = vehicleRepo.findById(vehicleId).orElseThrow(() -> new VehicleNotFoundException(vehicleId.toString()));
        var contract = Contract.builder()
                .customer(customer)
                .vehicle(vehicle)
                .currentMonthlyRate(contact.getMonthlyRate())
                .status(ContractStatus.ACTIVE)
                .contractHistory(Arrays.asList(new ContractRateHistory(contact.getMonthlyRate())))
                .build();
        return mapper.toContactDto(contractRepo.save(contract));
    }

    @Transactional
    public ContractDto updateContract(Long contactId, ContractDto contact){
        var contract =  contractRepo.findById(contactId).orElseThrow(() -> new ContractNotFoundException("Contract " + contactId + "not found"));
        if (ContractStatus.CLOSED == contract.getStatus()) {
            throw new ContractClosedException("Contract" + contract.getId() + " is closed at " + contract.getDeletedAt());
        }
        if (!contract.getCurrentMonthlyRate().equals(contact.getMonthlyRate())){
            contract.getContractHistory().add(new ContractRateHistory(contact.getMonthlyRate()));
            contract.setCurrentMonthlyRate(contact.getMonthlyRate());
        }
        return mapper.toContactDto(contractRepo.save(contract));
    }

    @Transactional
    public List<ContractDto> getContacts(Long contractId, Long customerId, Long vehicleId, ContractStatus status){
        var contract = contractRepo.findAll(
                SpecificationBuilder.builder()
                        .contractId(contractId)
                        .customerId(customerId)
                        .vehicleId(vehicleId)
                        .status(status)
                        .build()
        );
        return mapper.toContactDto(contract);
    }

    @Transactional
    public ContractDto getContact(Long contractId){
        var contract = contractRepo.findById(contractId)
                .orElseThrow(() -> new ContractNotFoundException("Contract " + contractId + " not found"));
        return mapper.toContactDto(contract);
    }

    @Transactional
    public ContractDto getContactByCustomerIdAndVehicleId(Long customerId, Long vehicleId){
        var contract = contractRepo.findByCustomerIdAndVehicleId(customerId, vehicleId)
                .orElseThrow(() -> new ContractNotFoundException("Contract for customer " + customerId + " and vehicle" + vehicleId + " not found"));
        return mapper.toContactDto(contract);
    }

    @Transactional
    public void deleteContract(Long contactId){
        contractRepo.softDeleteContractById(contactId, ContractStatus.CLOSED);
    }

}
