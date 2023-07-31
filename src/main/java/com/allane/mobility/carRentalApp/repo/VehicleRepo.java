package com.allane.mobility.carRentalApp.repo;

import com.allane.mobility.carRentalApp.dto.VehicleDto;
import com.allane.mobility.carRentalApp.model.Vehicle;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Root;
import java.util.List;

import static java.util.Objects.nonNull;

public interface VehicleRepo extends JpaRepository<Vehicle, Long>, EmRepository {

    @EntityGraph("vehicle-entity-graph")
    List<Vehicle> findAll();

    default void updateVehicle(Long vehicleId, VehicleDto vehicleDto) {
        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
        CriteriaUpdate<Vehicle> criteriaUpdate = cb.createCriteriaUpdate(Vehicle.class);
        Root<Vehicle> root = criteriaUpdate.from(Vehicle.class);
        if (nonNull(vehicleDto.getBrand())) {
            criteriaUpdate.set("brand", vehicleDto.getBrand());
        }
        if (nonNull(vehicleDto.getModel())) {
            criteriaUpdate.set("model", vehicleDto.getModel());
        }
        if (nonNull(vehicleDto.getModelYear())) {
            criteriaUpdate.set("modelYear", vehicleDto.getModelYear());
        }
        if (nonNull(vehicleDto.getVin())) {
            criteriaUpdate.set("vin", vehicleDto.getVin());
        }
        if (nonNull(vehicleDto.getPrice())) {
            criteriaUpdate.set("price", vehicleDto.getPrice());
        }
        criteriaUpdate.where(cb.equal(root.get("id"), vehicleId));
        getEntityManager().createQuery(criteriaUpdate).executeUpdate();
    }

}
