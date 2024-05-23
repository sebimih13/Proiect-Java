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

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (5, 'Cuba Libre', 'Rom, Suc lamaie, Cola', 19);

INSERT INTO bautura (id_produs, ml)
VALUES (5, 250);

INSERT INTO prepara (id_angajat, id_produs)
VALUES (5, 5);

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (6, 'Pina Colada', 'rom alb, suc de ananas, lapte de cocos', 10);

INSERT INTO bautura (id_produs, ml)
VALUES (6, 250);

INSERT INTO prepara (id_angajat, id_produs)
VALUES (5, 6);

INSERT INTO PRODUS (id_produs, nume, descriere, pret)
VALUES (7, 'Tochitura Moldoveneasca', 'porc, piept de pui, carnati, sos rosu cu usturoi, mamaliga, ou, branza, vin', 45);

INSERT INTO preparat (id_produs, grame)
VALUES (7, 500);

INSERT INTO gateste (id_angajat, id_produs)
VALUES (3, 7);

-- RESTAURANT 20
INSERT INTO restaurant (id_restaurant, nume, nr_stele, oras, strada, nr_telefon)
VALUES (20, 'Zen Garden Sushi', 5, 'Bucuresti', 'Strada Japonia nr. 12', '0775570200');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (7, NULL, 20, 'yamato', '123', 'Yamamoto', 'Takeshi', 7000, '0744231258');

INSERT INTO manager (id_angajat, nivel_educatie)
VALUES (7, 'Tokyo Business School');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (8, 7, 20, 'nakamura', '123', 'Nakamura', 'Emi', 4000, '0744431158');

INSERT INTO ospatar (id_angajat, nivel_engleza)
VALUES (8, 'B1');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (9, 7, 20, 'sato', '123', 'Sato', 'Hiroshi', 4500, '0744431682');

INSERT INTO ospatar (id_angajat, nivel_engleza)
VALUES (9, 'B2');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (10, 7, 20, 'suzuki', '123', 'Suzuki', 'Kazuki', 6400, '0744431458');

INSERT INTO sef_bucatar (id_angajat, specializare)
VALUES (10, 'Bucataria din Japonia');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (11, 7, 20, 'ryo', '123', 'Watanabe', 'Ryo', 6600, '0744123258');

INSERT INTO sef_bucatar (id_angajat, specializare)
VALUES (11, 'Bucataria din Japonia');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (12, 7, 20, 'kenji', '123', 'Takahashi', 'Kenji', 6800, '0744631559');

INSERT INTO sef_bucatar (id_angajat, specializare)
VALUES (12, 'Bucataria din Japonia');

INSERT INTO angajat (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)
VALUES (13, 7, 20, 'misaki', '123', 'Ishikawa', 'Misaki', 5000, '0744643658');

INSERT INTO barman (id_angajat, specializare)
VALUES (13, 'Cafea');

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (8, 'Cafe Latte', 'Cafea, Lapte, Crema de lapte', 17);

INSERT INTO bautura (id_produs, ml)
VALUES (8, 250);

INSERT INTO prepara (id_angajat, id_produs)
VALUES (13, 8);

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (9, 'Sushi Salmon Dragon', 'Orez, somon, praz, ardei iute, sriracha, sos de maioneza picanta', 60);

INSERT INTO preparat (id_produs, grame)
VALUES (9, 300);

INSERT INTO gateste (id_angajat, id_produs)
VALUES (10, 9);

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (10, 'Onigirazu', 'Orez, vitel, ou, alga, pesmet', 60);

INSERT INTO preparat (id_produs, grame)
VALUES (10, 300);

INSERT INTO gateste (id_angajat, id_produs)
VALUES (11, 10);

INSERT INTO produs (id_produs, nume, descriere, pret)
VALUES (11, 'Sushi California', 'Orez sushi, creveti, avocado, castraveti, susan, maioneza', 60);

INSERT INTO preparat (id_produs, grame)
VALUES (11, 300);

INSERT INTO gateste (id_angajat, id_produs)
VALUES (12, 11);

-- CLIENTI
INSERT INTO client (id_client, username, password, nume, prenume, nr_telefon, email)
VALUES (1, 'teo', '123', 'Teohari', 'Claudiu', '0774830111', 'claudiu.teohari@cnsc.com');

INSERT INTO client (id_client, username, password, nume, prenume, nr_telefon, email)
VALUES (2, 'tomi', '123', 'Popescu', 'Tomi', '0775235987', 'tomi@fr.com');

-- COMENZI
INSERT INTO comanda (id_comanda, id_client, id_restaurant, status, data, ora)
VALUES (1, 1, 10, "Livrata", '2024-05-23', '14:30:00');

INSERT INTO comanda (id_comanda, id_client, id_restaurant, status, data, ora)
VALUES (2, 1, 10, "Livrata", '2024-05-22', '14:30:00');

INSERT INTO comanda (id_comanda, id_client, id_restaurant, status, data, ora)
VALUES (3, 2, 20, "Livrata", '2024-05-20', '14:30:00');

-- CONTINE
INSERT INTO contine (id_produs, id_comanda, cantitate) 
VALUES (1, 1, 2);

INSERT INTO contine (id_produs, id_comanda, cantitate) 
VALUES (3, 1, 1);

INSERT INTO contine (id_produs, id_comanda, cantitate) 
VALUES (4, 1, 1);

INSERT INTO contine (id_produs, id_comanda, cantitate) 
VALUES (5, 2, 1);

INSERT INTO contine (id_produs, id_comanda, cantitate) 
VALUES (7, 2, 1);

INSERT INTO contine (id_produs, id_comanda, cantitate) 
VALUES (8, 3, 3);

INSERT INTO contine (id_produs, id_comanda, cantitate) 
VALUES (9, 3, 1);

INSERT INTO contine (id_produs, id_comanda, cantitate) 
VALUES (10, 3, 1);

INSERT INTO contine (id_produs, id_comanda, cantitate) 
VALUES (11, 3, 1);

