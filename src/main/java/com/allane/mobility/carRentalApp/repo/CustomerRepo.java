package com.allane.mobility.carRentalApp.repo;

import com.allane.mobility.carRentalApp.dto.CustomerDto;
import com.allane.mobility.carRentalApp.model.Customer;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;

import java.util.List;

import static java.util.Objects.nonNull;

public interface CustomerRepo extends JpaRepository<Customer, Long>, EmRepository {

    @EntityGraph("customer-entity-graph")
    List<Customer> findAll();

     default void updateCustomer(Long customerId, CustomerDto customerDto) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Customer> criteriaUpdate = cb.createCriteriaUpdate(Customer.class);
        Root<Customer> root = criteriaUpdate.from(Customer.class);
        if (nonNull(customerDto.getFirstName())) {
            criteriaUpdate.set("firstName", customerDto.getFirstName());
        }
        if (nonNull(customerDto.getLastName())) {
            criteriaUpdate.set("lastName", customerDto.getLastName());
        }
        if (nonNull(customerDto.getDateOfBirth())) {
            criteriaUpdate.set("dateOfBirth", customerDto.getDateOfBirth());
        }
         criteriaUpdate.where(cb.equal(root.get("id"), customerId));
         getEntityManager().createQuery(criteriaUpdate).executeUpdate();
    }

}

