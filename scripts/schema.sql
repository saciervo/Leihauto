-- noinspection SqlDialectInspectionForFile

DROP TABLE IF EXISTS accounts;
CREATE TABLE accounts (
    id      INTEGER       PRIMARY KEY AUTOINCREMENT,
    type    INTEGER       NOT NULL,
    name    STRING (80)   NOT NULL,
    street  STRING (120)  NOT NULL,
    zipCode STRING (10)   NOT NULL,
    city    STRING (50)   NOT NULL
);

DROP TABLE IF EXISTS locations;
CREATE TABLE locations (
    id                  INTEGER     PRIMARY KEY AUTOINCREMENT,
    street              STRING (80) NOT NULL,
    zipCode             STRING (10) NOT NULL,
    city                STRING (50) NOT NULL,
    amountParkingSpaces INTEGER
);

DROP TABLE IF EXISTS members;
CREATE TABLE members (
    id                   INTEGER      PRIMARY KEY AUTOINCREMENT,
    accountId            INTEGER      REFERENCES accounts (id),
    name                 STRING (80)  NOT NULL,
    postalAddress        STRING (350),
    defaultLocationId    INTEGER      REFERENCES locations (id),
    emailAddress         STRING (180) UNIQUE NOT NULL,
    pinCode              STRING (6)   NOT NULL,
    drivingLicenceNumber STRING (30)  UNIQUE NOT NULL,
    homePhoneNumber      STRING (20),
    mobilePhoneNumber    STRING (20),
    businessPhoneNumber  STRING (20),
    faxPhoneNumber       STRING (20) 
);

DROP TABLE IF EXISTS carCategories;
CREATE TABLE carCategories (
    id                  INTEGER      PRIMARY KEY AUTOINCREMENT,
    name                STRING (25)  NOT NULL,
    basicConfiguration  STRING (400)
);

DROP TABLE IF EXISTS cars;
CREATE TABLE cars (
    id                    INTEGER     PRIMARY KEY AUTOINCREMENT,
    parkingSpotLocationId INTEGER     REFERENCES locations (id),
    carCategoryId         INTEGER     REFERENCES carCategories (id),
    name                  STRING (40) NOT NULL,
    plateNumber           STRING (20) NOT NULL
);

DROP TABLE IF EXISTS reservations;
CREATE TABLE reservations (
    id        INTEGER  PRIMARY KEY AUTOINCREMENT,
    carId     INTEGER  REFERENCES cars (id),
    memberId  INTEGER  REFERENCES members (id),
    startDate DATETIME NOT NULL,
    endDate   DATETIME NOT NULL
);
