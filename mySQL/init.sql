-- ----------------
-- CREARE TABELE
-- ----------------

CREATE TABLE restaurant
(
    id_restaurant INT(5) UNSIGNED,

    nume VARCHAR(50) NOT NULL,
    nr_stele INT(1) UNSIGNED,
    nr_telefon VARCHAR(20),

    PRIMARY KEY (id_restaurant),
    CHECK (nr_stele >= 1 AND nr_stele <= 5),
    UNIQUE (nr_telefon)
);

CREATE TABLE angajat
(
    id_angajat INT(5) UNSIGNED,
    id_manager INT(5) UNSIGNED,
    id_restaurant INT(5) UNSIGNED,

    nume VARCHAR(50) NOT NULL,
    prenume VARCHAR(50) NOT NULL,
    salariu DECIMAL(10, 2) UNSIGNED NOT NULL,
    nr_telefon VARCHAR(20),

    PRIMARY KEY (id_angajat),
    FOREIGN KEY (id_manager) REFERENCES angajat(id_angajat),
    FOREIGN KEY (id_restaurant) REFERENCES angajat(id_angajat),
    UNIQUE (nr_telefon)
);

CREATE TABLE manager
(

);

CREATE TABLE sef_bucatar
(

);

CREATE TABLE barman
(

);

CREATE TABLE ospatar
(

);

-- ----------------
-- INSERARE DATE
-- ----------------

