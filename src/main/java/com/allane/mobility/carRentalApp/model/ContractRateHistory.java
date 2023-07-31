package com.allane.mobility.carRentalApp.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "contract_rate_history")
@NoArgsConstructor
@RequiredArgsConstructor
@Getter
@Setter
public class ContractRateHistory extends BaseEntity {

    @NonNull
    @Column(name = "monthly_rate")
    private BigDecimal monthlyRate;

    @ManyToOne(fetch = FetchType.LAZY)
    private Contract contract;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

}
