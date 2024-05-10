--------------------
-- STERGERE TABELE
--------------------

DROP TABLE prepara;
DROP TABLE gateste;
DROP TABLE contine;

DROP TABLE comanda;
DROP TABLE client;

DROP TABLE bautura;
DROP TABLE preparat;
DROP TABLE produs;

DROP TABLE ospatar;
DROP TABLE barman;
DROP TABLE sef_bucatar;
DROP TABLE manager;
DROP TABLE angajat;
DROP TABLE restaurant;

-- ------------------
-- STERGERE INSERARI
-- ------------------

DELETE FROM restaurant;
DELETE FROM angajat;
DELETE FROM manager;
DELETE FROM sef_bucatar;
DELETE FROM barman;
DELETE FROM ospatar;

DELETE FROM produs;
DELETE FROM preparat;
DELETE FROM bautura;

DELETE FROM client;
DELETE FROM comanda;

DELETE FROM contine;
DELETE FROM gateste;
DELETE FROM prepara;

-- -----------
-- SELECTIE
-- -----------

SELECT * FROM restaurant;
SELECT * FROM angajat;
SELECT * FROM manager;
SELECT * FROM sef_bucatar;
SELECT * FROM barman;
SELECT * FROM ospatar;

SELECT * FROM produs;
SELECT * FROM preparat;
SELECT * FROM bautura;

SELECT * FROM client;
SELECT * FROM comanda;

SELECT * FROM contine;
SELECT * FROM gateste;
SELECT * FROM prepara;

-- ------
-- TEST
-- ------

SELECT a.id_angajat, a.id_manager, a.id_restaurant, a.username, a.password, a.nume, a.prenume, a.salariu, a.nr_telefon
FROM angajat a JOIN manager sb ON (a.id_angajat = sb.id_angajat);

SELECT *
FROM restaurant;

DELETE FROM angajat
WHERE id_angajat = 7;

