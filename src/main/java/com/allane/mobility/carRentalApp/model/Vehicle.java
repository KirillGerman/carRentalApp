package com.allane.mobility.carRentalApp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "vehicles")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@NamedEntityGraph(
        name = "vehicle-entity-graph",
        attributeNodes = {
                @NamedAttributeNode(value = "contract" , subgraph = "contract.customer")
        },
        subgraphs = {
                @NamedSubgraph(name = "contract.customer",
                        attributeNodes = @NamedAttributeNode(value = "customer"))
        }
)
public class Vehicle extends BaseEntity{

    @Column(name = "brand")
    private String brand;

    @Column(name = "model")
    private String model;

    @Column(name = "model_year")
    private LocalDate modelYear;

    @Column(name = "vin")
    private String vin;

    @Column(name = "price")
    private BigDecimal price;

    @OneToOne(mappedBy = "vehicle")
    private Contract contract;

}
