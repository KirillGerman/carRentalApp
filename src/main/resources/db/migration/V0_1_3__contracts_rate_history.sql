CREATE TABLE contract_rate_history (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    monthly_rate DECIMAL(12,2) CHECK (monthly_rate > 0) NOT NULL,
    contract_id BIGINT,
    created_at TIMESTAMP NOT NULL,
    FOREIGN KEY (contract_id) REFERENCES contracts(id),
    INDEX (contract_id)
);