BEGIN TRANSACTION;

-- Accounts
INSERT INTO accounts (id, type, name, street, zipCode, city)
VALUES (1, 0, 'Sandro Hirscher', 'Marcelstrasse 21', '8005', 'Zürich');
INSERT INTO accounts (id, type, name, street, zipCode, city)
VALUES (2, 0, 'Andrea Öfeli', 'Schwundgasse 17', '8802', 'Kilchberg');
INSERT INTO accounts (id, type, name, street, zipCode, city)
VALUES (3, 1, 'Hochschule für Wirtschaft Zürich', 'Lagerstrasse 5', '8021', 'Zürich');

-- Locations
INSERT INTO locations (id, street, zipCode, city, amountParkingSpaces)
VALUES (1, 'Gessnerallee 54', '8001', 'Zürich', 2);
INSERT INTO locations (id, street, zipCode, city, amountParkingSpaces)
VALUES (2, 'Werdmühleplatz 6', '8001', 'Zürich', 3);

-- Members
INSERT INTO members (id, accountId, name, postalAddress, defaultLocationId, emailAddress, pinCode, drivingLicenceNumber, homePhoneNumber, mobilePhoneNumber, businessPhoneNumber, faxPhoneNumber)
VALUES (1, 1, 'Sandro Hirscher', NULL, 1, 'sandro@hirscher.ch', '028632', '006911875552', NULL, NULL, NULL, NULL);
INSERT INTO members (id, accountId, name, postalAddress, defaultLocationId, emailAddress, pinCode, drivingLicenceNumber, homePhoneNumber, mobilePhoneNumber, businessPhoneNumber, faxPhoneNumber)
VALUES (2, 2, 'Andrea Öfeli', NULL, 2, 'andreaoefeli@bluewin.ch', '273840', '008514035212', NULL, NULL, NULL, NULL);
INSERT INTO members (id, accountId, name, postalAddress, defaultLocationId, emailAddress, pinCode, drivingLicenceNumber, homePhoneNumber, mobilePhoneNumber, businessPhoneNumber, faxPhoneNumber)
VALUES (3, 3, 'Steven Mountain', NULL, 1, 'steve@mountain-engineering.ch', '324911', '030290735920', NULL, NULL, NULL, NULL);
INSERT INTO members (id, accountId, name, postalAddress, defaultLocationId, emailAddress, pinCode, drivingLicenceNumber, homePhoneNumber, mobilePhoneNumber, businessPhoneNumber, faxPhoneNumber)
VALUES (4, 3, 'Michael Mayo', NULL, 1, 'michi.mayo@fh-hwz.ch', '007123', '000274032525', NULL, NULL, NULL, NULL);

-- CarCategories
INSERT INTO carCategories (id, name, basicConfiguration) VALUES (1, 'Limousine', 'Ledersitze, Sitzheizung');
INSERT INTO carCategories (id, name, basicConfiguration) VALUES (2, 'Kombi', '');
INSERT INTO carCategories (id, name, basicConfiguration) VALUES (3, 'Elektro', 'Freisprechanlage');

-- Cars
INSERT INTO cars (id, parkingSpotLocationId, carCategoryId, name, plateNumber)
VALUES (1, 1, 1, 'Aston Martin DB9', 'ZH-23632');
INSERT INTO cars (id, parkingSpotLocationId, carCategoryId, name, plateNumber)
VALUES (2, 2, 2, 'Opel Corsa', 'ZH-392811');
INSERT INTO cars (id, parkingSpotLocationId, carCategoryId, name, plateNumber)
VALUES (3, 2, 2, 'Seat Leon', 'ZH-593236');
INSERT INTO cars (id, parkingSpotLocationId, carCategoryId, name, plateNumber)
VALUES (4, 1, 3, 'Renault ZOE', 'ZH-845721');

-- Reservations
INSERT INTO reservations (id, carId, memberId, startDate, endDate)
VALUES (1, 1, 4, '2016-01-26 15:00', '2016-01-26 22:00');
INSERT INTO reservations (id, carId, memberId, startDate, endDate)
VALUES (2, 3, 1, '2016-01-24 10:00', '2016-01-24 12:00');
INSERT INTO reservations (id, carId, memberId, startDate, endDate)
VALUES (3, 4, 2, '2016-01-23 22:00', '2016-01-24 20:00');
INSERT INTO reservations (id, carId, memberId, startDate, endDate)
VALUES (4, 1, 3, '2016-01-26 07:30', '2016-01-26 19:00');

COMMIT TRANSACTION;