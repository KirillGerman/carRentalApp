package com.allane.mobility.carRentalApp.utils;

import com.allane.mobility.carRentalApp.model.Contract;
import com.allane.mobility.carRentalApp.model.ContractStatus;
import org.springframework.data.jpa.domain.Specification;

import static java.util.Objects.nonNull;

public class SpecificationBuilder {


    public static SpecificationBuilder.Builder builder() {
        return new SpecificationBuilder.Builder();
    }

    public static class Builder {

        private Specification<Contract> filter = Specification.where(null);

        public SpecificationBuilder.Builder contractId(Long contractId) {
            if (nonNull(contractId)) {
                filter = filter.and((contract, cq, cb) -> cb.equal(contract.get("id"), contractId));
            }
            return this;
        }

        public SpecificationBuilder.Builder customerId(Long customerId) {
            if (nonNull(customerId)) {
                filter = filter.and((contract, cq, cb) -> cb.equal(contract.join("customer").get("id"), customerId));
            }
            return this;
        }

        public SpecificationBuilder.Builder vehicleId(Long vehicleId) {
            if (nonNull(vehicleId)) {
                filter = filter.and((contract, cq, cb) -> cb.equal(contract.join("vehicle").get("id"), vehicleId));
            }
            return this;
        }

        public SpecificationBuilder.Builder status(ContractStatus status) {
            if (nonNull(status)) {
                filter = filter.and((contract, cq, cb) -> cb.equal(contract.get("status"), status));
            }
            return this;
        }

        public Specification<Contract> build() {
            return filter;
        }

    }


}