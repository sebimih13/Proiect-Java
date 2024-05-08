package model;

public class Ospatar extends Angajat {
    String nivelEngleza;

    public Ospatar(String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String nivelEngleza) {
        super(username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.nivelEngleza = nivelEngleza;
    }
}

