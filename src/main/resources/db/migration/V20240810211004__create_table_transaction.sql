CREATE TABLE transaction (
    id UUID PRIMARY KEY,
    account_id BIGINT NOT NULL,
    amount NUMERIC(19, 2) NOT NULL,
    merchant VARCHAR(255) NOT NULL,
    mcc VARCHAR(255) NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(id)
);