package com.allane.mobility.carRentalApp.repo;

import com.allane.mobility.carRentalApp.model.Contract;
import com.allane.mobility.carRentalApp.model.ContractStatus;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface ContractRepo extends JpaRepository<Contract, Long> {

    @Modifying
    @Query("update Contract contract set contract.status = :status, deletedAt = CURRENT_TIMESTAMP where contract.id = :contractId")
    void softDeleteContractById(@Param("contractId") Long contractId, @Param("status") ContractStatus status );

    @Query("select contract from Contract contract where contract.status = :status")
    List<Contract> findAllByStatus(@Param("status") ContractStatus status);

    Optional<Contract> findByIdAndStatus(Long contractId, ContractStatus status);

    @EntityGraph("contract-entity-graph")
    Optional<Contract> findByCustomerIdAndVehicleId(Long customerId, Long vehicleId);

    Optional<Contract> findByVehicleId(Long vehicleId);

    @EntityGraph("contract-entity-graph")
    List<Contract> findAll(Specification<Contract> spec);

    Optional<Contract> findById(Long contactId);


}
