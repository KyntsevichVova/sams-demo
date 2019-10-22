CREATE DATABASE IF NOT EXISTS db_demo;

CREATE TABLE IF NOT EXISTS QUESTION (
    ID INT NOT NULL AUTO_INCREMENT,
    TITLE VARCHAR(100) NOT NULL,
    LINK VARCHAR(100) NOT NULL,
    LEVEL VARCHAR(10) NOT NULL,
    PRIMARY KEY(ID)
);