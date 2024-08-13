CREATE TABLE account (
    id BIGSERIAL PRIMARY KEY,
    number VARCHAR(255) NOT NULL,
    food_balance NUMERIC(19, 2) NOT NULL,
    meal_balance NUMERIC(19, 2) NOT NULL,
    cash_balance NUMERIC(19, 2) NOT NULL
);