-- ----------------
-- CREARE TABELE
-- ----------------

CREATE TABLE restaurant
(
    id_restaurant INT(5) UNSIGNED,

    nume VARCHAR(255) NOT NULL,
    nr_stele INT(1) UNSIGNED,
    oras VARCHAR(255) NOT NULL,
    strada VARCHAR(255) NOT NULL,
    nr_telefon VARCHAR(10),

    PRIMARY KEY (id_restaurant),
    CHECK (nr_stele >= 1 AND nr_stele <= 5),
    UNIQUE (nr_telefon)
);

CREATE TABLE angajat
(
    id_angajat INT(5) UNSIGNED,
    id_manager INT(5) UNSIGNED,
    id_restaurant INT(5) UNSIGNED,

    username VARCHAR(255),
    password VARCHAR(255),

    nume VARCHAR(255) NOT NULL,
    prenume VARCHAR(255) NOT NULL,
    salariu INT(10) UNSIGNED NOT NULL,
    nr_telefon VARCHAR(10),

    PRIMARY KEY (id_angajat),
    FOREIGN KEY (id_manager) REFERENCES angajat(id_angajat),
    FOREIGN KEY (id_restaurant) REFERENCES restaurant(id_restaurant),
    UNIQUE (username),
    UNIQUE (nr_telefon)
);

CREATE TABLE manager
(
    id_angajat INT(5) UNSIGNED,

    nivel_educatie VARCHAR(255) NOT NULL,

    PRIMARY KEY (id_angajat),
    FOREIGN KEY (id_angajat) REFERENCES angajat(id_angajat)
);

CREATE TABLE sef_bucatar
(
    id_angajat INT(5) UNSIGNED,

    specializare VARCHAR(255),

    PRIMARY KEY (id_angajat),
    FOREIGN KEY (id_angajat) REFERENCES angajat(id_angajat)
);

CREATE TABLE barman
(
    id_angajat INT(5) UNSIGNED,

    specializare VARCHAR(255),

    PRIMARY KEY (id_angajat),
    FOREIGN KEY (id_angajat) REFERENCES angajat(id_angajat)
);

CREATE TABLE ospatar
(
    id_angajat INT(5) UNSIGNED,

    nivel_engleza VARCHAR(255),

    PRIMARY KEY (id_angajat),
    FOREIGN KEY (id_angajat) REFERENCES angajat(id_angajat)
);

CREATE TABLE produs
(
    id_produs INT(5) UNSIGNED,

    nume VARCHAR(255) NOT NULL,
    descriere VARCHAR(511) NOT NULL,
    pret INT(10) UNSIGNED NOT NULL,

    PRIMARY KEY (id_produs),
    CHECK (pret > 0)
);

CREATE TABLE preparat
(
    id_produs INT(5) UNSIGNED,

    grame INT(5) UNSIGNED,

    PRIMARY KEY (id_produs),
    FOREIGN KEY (id_produs) REFERENCES produs(id_produs),
    CHECK (grame > 0)
);

CREATE TABLE bautura
(
    id_produs INT(5) UNSIGNED,

    ml INT(5) UNSIGNED,

    PRIMARY KEY (id_produs),
    FOREIGN KEY (id_produs) REFERENCES produs(id_produs),
    CHECK (ml > 0)
);

CREATE TABLE client
(
    id_client INT(5) UNSIGNED,

    username VARCHAR(255),
    password VARCHAR(255),

    nume VARCHAR(255) NOT NULL,
    prenume VARCHAR(255) NOT NULL,
    nr_telefon VARCHAR(10),
    email VARCHAR(255),

    PRIMARY KEY (id_client),
    UNIQUE (username),
    UNIQUE (nr_telefon),
    UNIQUE (email)
);

CREATE TABLE comanda
(
    id_comanda INT(5) UNSIGNED,
    id_client INT(5) UNSIGNED,
    id_restaurant INT(5) UNSIGNED,

    status VARCHAR(255),
    data DATE,
    ora TIME,

    PRIMARY KEY (id_comanda),
    FOREIGN KEY (id_client) REFERENCES client(id_client),
    FOREIGN KEY (id_restaurant) REFERENCES restaurant(id_restaurant)
);

CREATE TABLE contine
(
    id_produs INT(5) UNSIGNED,
    id_comanda INT(5) UNSIGNED,

    cantitate INT(5) UNSIGNED DEFAULT 1,

    PRIMARY KEY (id_produs, id_comanda),
    FOREIGN KEY (id_produs) REFERENCES produs(id_produs) ON DELETE CASCADE,
    FOREIGN KEY (id_comanda) REFERENCES comanda(id_comanda) ON DELETE CASCADE,
    CHECK (cantitate >= 1)
);

CREATE TABLE GATESTE
(
    id_angajat INT(5) UNSIGNED,
    id_produs INT(5) UNSIGNED,

    PRIMARY KEY (id_angajat, id_produs),
    FOREIGN KEY (id_angajat) REFERENCES angajat(id_angajat) ON DELETE CASCADE,
    FOREIGN KEY (id_produs) REFERENCES produs(id_produs) ON DELETE CASCADE
);

CREATE TABLE PREPARA
(
    id_angajat INT(5) UNSIGNED,
    id_produs INT(5) UNSIGNED,

    PRIMARY KEY (id_angajat, id_produs),
    FOREIGN KEY (id_angajat) REFERENCES angajat(id_angajat) ON DELETE CASCADE,
    FOREIGN KEY (id_produs) REFERENCES produs(id_produs) ON DELETE CASCADE
);

-- ----------------
-- INSERARE DATE
-- ----------------

-- RESTAURANT 10
INSERT INTO restaurant (id_restaurant, nume, nr_stele, oras, strada, nr_telefon)
VALUES (10, 'International Bistro', 4, 'Bucuresti', 'Strada Magnolia nr. 54', '0775570151');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (1, NULL, 10, 'stoica_vlad', '123', 'Stoica', 'Vlad', 9000, '0720111111');

INSERT INTO manager (id_angajat, nivel_educatie)
VALUES (1, 'ASE - Facultatea de administrare a afacerilor');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (2, 1, 10, 'd_ana', '123', 'Dumitrescu', 'Ana', 4500, '0720111222');

INSERT INTO ospatar (id_angajat, nivel_engleza)
VALUES (2, 'B2');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (3, 1, 10, 'petrescu', '123', 'Petrescu', 'Ionela', 7000, '0720100333');

INSERT INTO sef_bucatar (id_angajat, specializare)
VALUES (3, 'Bucataria din Romania');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (4, 1, 10, 'preda_mihai', '123', 'Preda', 'Mihai', 7500, '0720111444');

INSERT INTO sef_bucatar (id_angajat, specializare)
VALUES (4, 'Bucataria din Italia');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (5, 1, 10, 'ga', '123', 'Georgescu', 'Adrian', 4500, '0720111555');

INSERT INTO barman (id_angajat, specializare)
VALUES (5, 'Cocktail');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (6, 1, 10, 'im', '123', 'Ionescu', 'Marius', 5000, '0720111666');

INSERT INTO barman (id_angajat, specializare)
VALUES (6, 'Milkshake');

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (1, 'Mocca Milkshake', 'cafea, finetti, lapte, inghetata', 21);

INSERT INTO bautura (id_produs, ml)
VALUES (1, 300);

INSERT INTO prepara (id_angajat, id_produs)
VALUES (6, 1);

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (2, 'Milkshake Banane', 'banane, lapte', 16);

INSERT INTO bautura (id_produs, ml)
VALUES (2, 400);

INSERT INTO prepara (id_angajat, id_produs)
VALUES (6, 2);

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (3, 'Pui Italian', 'piept de pui, mozzarella, rosii, vinete, dovlecei, salata mix', 41);

INSERT INTO preparat (id_produs, grame)
VALUES (3, 300);

INSERT INTO gateste (id_angajat, id_produs)
VALUES (4, 3);

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (4, 'Spaghete Milaneze', 'spaghete, sunca, ciuperci, ceapa, rosii, condimente/parmezan', 33);

INSERT INTO preparat (id_produs, grame)
VALUES (4, 400);

INSERT INTO gateste (id_angajat, id_produs)
VALUES (4, 4);

-- RESTAURANT 20
INSERT INTO restaurant (id_restaurant, nume, nr_stele, oras, strada, nr_telefon)
VALUES (20, 'Zen Garden Sushi', 5, 'Bucuresti', 'Strada Japonia nr. 12', '0775570200');

