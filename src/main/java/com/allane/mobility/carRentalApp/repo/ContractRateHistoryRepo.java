package com.allane.mobility.carRentalApp.repo;

import com.allane.mobility.carRentalApp.model.ContractRateHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ContractRateHistoryRepo extends JpaRepository<ContractRateHistory, Long> {

    List<ContractRateHistory> findByContractId(Long contractId);

}
