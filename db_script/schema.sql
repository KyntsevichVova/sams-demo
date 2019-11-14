CREATE DATABASE IF NOT EXISTS db_demo;

USE db_demo;

CREATE TABLE IF NOT EXISTS `LEVEL` (
    ID INT NOT NULL AUTO_INCREMENT,
    LEVEL_NAME VARCHAR(10) NOT NULL,
    PRIMARY KEY(ID)
);

CREATE TABLE IF NOT EXISTS QUESTION (
    ID INT NOT NULL AUTO_INCREMENT,
    TITLE VARCHAR(255) NOT NULL,
    LINK VARCHAR(255) NOT NULL,
    LEVEL_ID INT NOT NULL,
    PRIMARY KEY(ID),
    FOREIGN KEY(LEVEL_ID) REFERENCES `LEVEL` (ID)
);