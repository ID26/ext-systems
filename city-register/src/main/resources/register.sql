DROP TABLE If EXISTS cr_address_person;
DROP TABLE If EXISTS cr_person;
DROP TABLE If EXISTS cr_address;
DROP TABLE If EXISTS cr_street;
DROP TABLE If EXISTS cr_district;


CREATE TABLE cr_district(
    district_code integer not null,
	district_name varchar(300),
	PRIMARY KEY (district_code)
);

INSERT INTO cr_district(district_code, district_name) VALUES
(1, 'Октябрьский');
(2, 'Первомайский');

CREATE TABLE cr_street(
	street_code integer not null,
	street_name varchar(300),
	PRIMARY KEY (street_code)
);

INSERT INTO cr_street(street_code, street_name) VALUES
(1, 'Таганрогская');
(2, 'Днепровский');

CREATE TABLE cr_address(
	address_id SERIAL,
	district_code integer not null,
	street_code integer not null,
	building varchar(10) not null,
    extension varchar(10),
    apartment varchar(10),
	PRIMARY KEY (address_id),
    FOREIGN KEY (district_code) REFERENCES cr_district(district_code) ON DELETE RESTRICT,
    FOREIGN KEY (street_code) REFERENCES cr_street(street_code) ON DELETE RESTRICT
);

INSERT INTO cr_address(district_code, street_code, building, extension, apartment) VALUES
(1, 1, '124', '3', '17');
(2, 2, '120', 'Б', '208');

CREATE TABLE cr_person(
    person_id SERIAL,
    sur_name varchar(100) not null,
    given_name varchar(100) not null,
    patronymic varchar(100) not null,
    date_of_birth date not null,
    passport_seria varchar(10),
    passport_number varchar(10),
    passport_date date,
    certificate_number varchar(10) null,
    certificate_date date null,
    PRIMARY KEY (person_id)
);

INSERT INTO cr_person(sur_name, given_name, patronymic, date_of_birth, passport_seria,
                        passport_number, passport_date, certificate_number, certificate_date) VALUES
('Денисов', 'Иван', 'Александрович', '1979-10-17', '0000', '123456', '1995-10-17', null, null),
('Денисова', 'Елена', 'Владимировна', '1980-01-17', '1111', '123456', '1996-01-17', null, null),
('Денисов', 'Никита', 'Иванович', '2010-02-01', null, null, null, '12345678', '2010-02-01'),
('Денисова', 'Алена', 'Ивановна', '2012-03-06', null, null, null, '23456789', '2012-03-06');

CREATE TABLE cr_address_person  (
    person_address_id SERIAL,
    address_id integer not null,
    person_id integer not   null,
    start_date  date    not null,
    end_date    date,
    temporal boolean DEFAULT false,
    PRIMARY KEY (person_address_id),
    FOREIGN KEY (address_id) REFERENCES cr_address(address_id) ON DELETE RESTRICT,
    FOREIGN KEY (person_id) REFERENCES cr_person(person_id) ON DELETE RESTRICT
    );

INSERT INTO cr_address_person(address_id, person_id, start_date, end_date, temporal) VALUES
    (2, 1, '2006-03-06', null, false),
    (1, 2, '2006-07-06', null, false),
    (1, 3, '2010-02-01', null, false),
    (1, 4, '2012-03-06', null, false);

-- 1. РАЙОН
-- 2. УЛИЦА
-- 3. АДРЕС - РАЙОН, УЛИЦА, ДОМ, КОРПУС, КВАРТИРА
-- 4. ПЕРСОНА - ФИО, ДАТА РОЖДЕНИЯ, ПАСПОРТ (СЕРИЯ/НОМЕР/ДАТА ВЫДАЧИ),
-- СВИДЕТЕЛЬСТВО О РОЖДЕНИИ(НОМЕР/ДАТА ВЫДАЧИ)
-- 5. СВЯЗЬ ПЕРСОНЫ И АДРЕСА - ПЕРСОНА, АДРЕС, ДИАПАЗОН ДАТ, ВИД РЕГИСТРАЦИИ

-- DDD - DOMAIN DRIVEN DEVELOPMENT