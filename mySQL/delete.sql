-- ------------------
-- STERGERE TABELE
-- ------------------

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
FROM angajat a JOIN ospatar sb ON (a.id_angajat = sb.id_angajat);

SELECT *
FROM restaurant;

DELETE FROM angajat
WHERE id_angajat = 7;

UPDATE angajat
SET salariu = 4000
WHERE id_angajat = 2;

SELECT p.id_produs, p.nume, p.descriere, p.pret, pr.grame, sb.id_angajat
FROM produs p JOIN preparat pr ON (p.id_produs = pr.id_produs)
              JOIN gateste g ON (p.id_produs = g.id_produs)
              JOIN sef_bucatar sb ON (g.id_angajat = sb.id_angajat);

SELECT p.id_produs, p.nume, p.descriere, p.pret, b.ml, bar.id_angajat
FROM produs p JOIN bautura b ON (p.id_produs = b.id_produs)
              JOIN prepara pre ON (p.id_produs = pre.id_produs)
              JOIN barman bar ON (pre.id_angajat = bar.id_angajat);

SELECT c.id_comanda, c.id_client, r.id_restaurant, c.status, c.data, c.ora
FROM comanda c JOIN restaurant r ON (c.id_restaurant = r.id_restaurant);

SELECT c.id_comanda, p.id_produs, con.cantitate
FROM comanda c JOIN contine con ON (c.id_comanda = con.id_comanda)
               JOIN produs p ON (con.id_produs = p.id_produs);

