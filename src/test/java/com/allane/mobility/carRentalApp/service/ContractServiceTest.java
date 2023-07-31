package com.allane.mobility.carRentalApp.service;

import com.allane.mobility.carRentalApp.dto.ContractDto;
import com.allane.mobility.carRentalApp.exception.ContractExistsException;
import com.allane.mobility.carRentalApp.model.ContractStatus;
import com.allane.mobility.carRentalApp.repo.ContractRateHistoryRepo;
import com.allane.mobility.carRentalApp.repo.ContractRepo;
import com.allane.mobility.carRentalApp.utils.DtoUtilsClass;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import java.math.BigDecimal;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
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
class ContractServiceTest {

    @Autowired ContractService contractService;
    @Autowired ContractRepo contractRepo;
    @Autowired VehicleService vehicleService;
    @Autowired ContractRateHistoryRepo contractRateHistoryRepo;


    @Test
    void shouldCreateContractAndAddToContractRateHistoryRepo(){
        var vehicle =  vehicleService.createVehicle(DtoUtilsClass.createVehicleDto());
        var contract = contractService.createContract(1L, vehicle.getId(), new ContractDto(BigDecimal.TEN));
        var contractHistory =  contractRateHistoryRepo.findByContractId(contract.getId());
        Assertions.assertNotNull(contractHistory.stream().findFirst().get().getContract());
    }


    @Test
    void shouldNotCreateContractWhetVehicleBoundToContract(){
        var vehicle =  vehicleService.createVehicle(DtoUtilsClass.createVehicleDto());
        contractService.createContract(1L, vehicle.getId(), new ContractDto(BigDecimal.TEN));
        Assertions.assertThrows(
                ContractExistsException.class,
                () -> contractService.createContract(1L, vehicle.getId(), new ContractDto(BigDecimal.TEN))
        );
    }

    @Test
    void shouldGetContractById(){
        var contract =  contractService.getContact(1L);
        Assertions.assertNotNull(contract);
    }

    @Test
    void shouldFindAllContractBySpec(){
        var contractsBySpec = contractService.getContacts(null,null, null, null);
        var contracts = contractRepo.findAll();
        Assertions.assertEquals(contractsBySpec.size(), contracts.size());
    }

    @Test
    void shouldUpdateContractByIdAndAddToContractRateHistoryRepo(){
        var initRate = BigDecimal.TEN;
        var newRate = initRate.multiply(BigDecimal.TEN);
        var contract =  contractService.updateContract(1L , new ContractDto(newRate));
        Assertions.assertEquals(newRate, contract.getMonthlyRate());
        var contractHistory =  contractRateHistoryRepo.findByContractId(contract.getId());
        assertThat(contractHistory, containsInAnyOrder(
                hasProperty("monthlyRate", is(initRate.setScale(2))),
                hasProperty("monthlyRate", is(newRate.setScale(2)))
        ));
    }

    @Test
    void shouldSoftDeleteContract(){
        contractService.deleteContract(1L);
        var contract =  contractService.getContact(1L);
        Assertions.assertEquals(ContractStatus.CLOSED, contract.getStatus());
    }

    @Test
    void shouldGetContractByCustomerIdAndVehicleId(){
        var contract = contractService.getContactByCustomerIdAndVehicleId(1L,2L);
        Assertions.assertNotNull(contract);
    }


    @Test
    void shouldGetContractBySpecification(){
        var contractsByContractId = contractService.getContacts(1L,null, null, null);
        var contractsByCustomerId = contractService.getContacts(null,1L, null, null);
        var contractsByVehicleId = contractService.getContacts(null,null, 1L, null);
        var contractsByStatus = contractService.getContacts(null,null, null, ContractStatus.ACTIVE);
        Assertions.assertNotNull(contractsByContractId);
        Assertions.assertNotNull(contractsByCustomerId);
        Assertions.assertNotNull(contractsByVehicleId);
        Assertions.assertNotNull(contractsByStatus);
    }



}

