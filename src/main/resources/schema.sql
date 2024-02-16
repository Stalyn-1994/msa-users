-- Create schema if not exist
CREATE SCHEMA IF NOT EXISTS users;

-- Create table person if not exist
CREATE TABLE IF NOT EXISTS users.customer
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    identification VARCHAR(255)             NOT NULL,
    name           VARCHAR(255)             NOT NULL,
    gender         VARCHAR(50),
    age            INT,
    address        VARCHAR(255),
    cellphone      VARCHAR(50),
    password       VARCHAR(255)             NOT NULL,
    client_id      VARCHAR(255)             NOT NULL,
    state          BOOLEAN                           DEFAULT FALSE,
    created_on     TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on     TIMESTAMP WITH TIME ZONE
);