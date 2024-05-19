create database Banking;

CREATE TABLE `Banking`.`Account` (
    `IBAN` VARCHAR(45) NOT NULL,
    `swift` VARCHAR(45) NULL,
    `balance` DOUBLE NULL,
    `name` VARCHAR(45) NULL,
    `userID` INT NOT NULL,
    PRIMARY KEY (`IBAN`)
);

CREATE TABLE `Banking`.`SavingsAccount` (
    `IBAN` VARCHAR(45) NOT NULL,
    `swift` VARCHAR(45) NULL,
    `balance` FLOAT NULL,
    `name` VARCHAR(45) NULL,
    `userID` INT NOT NULL,
    `startDate` DATE NULL,
    `endDate` DATE NULL,
    `interestRate` INT NULL,
    PRIMARY KEY (`IBAN`)
);

CREATE TABLE `Banking`.`Transaction` (
    `fromIBAN` VARCHAR(45) NOT NULL,
    `toIBAN` VARCHAR(45) NOT NULL,
    `description` VARCHAR(45) NULL,
    `amount` FLOAT NULL,
    `issuedDate` DATE,
    PRIMARY KEY (`fromIBAN`, `toIBAN`)
);

CREATE TABLE `Banking`.`User` (
    `userID` INT NOT NULL AUTO_INCREMENT,
    `firstName` VARCHAR(45) NULL,
    `lastName` VARCHAR(45) NULL,
    `CNP` VARCHAR(45) NULL,
    `emailAddress` VARCHAR(45) NULL,
    `phoneNumber` VARCHAR(45) NULL,
    `country` VARCHAR(45) NULL,
    `county` VARCHAR(45) NULL,
    `city` VARCHAR(45) NULL,
    `street` VARCHAR(45) NULL,
    `postalCode` INT NULL,
    PRIMARY KEY (`userID`)
);

CREATE TABLE `Banking`.`MasterCard` (
    `cardID` INT NOT NULL AUTO_INCREMENT,
    `CVV` INT NULL,
    `cardNumber` VARCHAR(45) NULL,
    `name` VARCHAR(45) NULL,
    `IBAN` VARCHAR(45) NULL,
    `expirationDate` DATE NULL,
    PRIMARY KEY (cardID)
);

CREATE TABLE `Banking`.`Visa` (
    `cardID` INT NOT NULL AUTO_INCREMENT,
    `CVV` INT NULL,
    `cardNumber` VARCHAR(45) NULL,
    `name` VARCHAR(45) NULL,
    `IBAN` VARCHAR(45) NULL,
    `expirationDate` DATE NULL,
    PRIMARY KEY (cardID)
);