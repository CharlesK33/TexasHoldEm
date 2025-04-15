CREATE DATABASE texas_holdem;

USE texas_holdem;

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARBINARY(255) NOT NULL,
    score INT DEFAULT 0
);
