CREATE TABLE contracts (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    current_monthly_rate DECIMAL(12,2) CHECK (current_monthly_rate > 0) NOT NULL,
    customer_id BIGINT,
    vehicle_id BIGINT,
    status INTEGER NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    deleted_at TIMESTAMP,
    UNIQUE (customer_id, vehicle_id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    FOREIGN KEY (vehicle_id) REFERENCES vehicles(id),
    INDEX (customer_id, vehicle_id),
    INDEX (vehicle_id),
    INDEX (customer_id)
);