-- Create schema if not exist
CREATE SCHEMA IF NOT EXISTS users;

CREATE OR REPLACE FUNCTION update_updated_on_column()
    RETURNS TRIGGER AS
$$
BEGIN
    NEW.updated_on = now();
    RETURN NEW;
END;
$$ language 'plpgsql';

-- Create table person if not exist
CREATE TABLE IF NOT EXISTS users.person
(
    id             SERIAL PRIMARY KEY,
    identification VARCHAR(255)             NOT NULL,
    name           VARCHAR(255)             NOT NULL,
    gender         VARCHAR(50),
    age            INT,
    address        VARCHAR(255),
    cellphone      VARCHAR(50),
    created_on     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on     TIMESTAMP WITH TIME ZONE
);


-- Create table customer if not exist
CREATE TABLE IF NOT EXISTS users.customer
(
    id             SERIAL PRIMARY KEY,
    password       VARCHAR(255)             NOT NULL,
    client_id      VARCHAR(255)             NOT NULL,
    state          BOOLEAN                           DEFAULT FALSE,
    created_on     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on     TIMESTAMP WITH TIME ZONE
);

CREATE TRIGGER document_updated_on_trigger
    BEFORE UPDATE
    ON users.customer
    FOR EACH ROW
EXECUTE PROCEDURE
    update_updated_on_column();

CREATE TRIGGER document_updated_on_trigger
    BEFORE UPDATE
    ON users.person
    FOR EACH ROW
EXECUTE PROCEDURE
    update_updated_on_column();