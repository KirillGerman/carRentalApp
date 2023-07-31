package com.allane.mobility.carRentalApp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "contracts")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@NamedEntityGraph(
        name = "contract-entity-graph",
        attributeNodes = {
                @NamedAttributeNode("customer"),
                @NamedAttributeNode("vehicle"),
        }
)
public class Contract extends BaseEntity {

    @Column(name = "current_monthly_rate")
    private BigDecimal currentMonthlyRate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @OneToOne(fetch = FetchType.LAZY)
    @LazyToOne(value = LazyToOneOption.NO_PROXY)
    private Vehicle vehicle;

    @Enumerated
    private ContractStatus status;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "contract_id")
    private List<ContractRateHistory> contractHistory;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private LocalDateTime deletedAt;

}
