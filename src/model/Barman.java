package model;

public class Barman extends Angajat {
    String specializare;

    public Barman(String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String specializare) {
        super(username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.specializare = specializare;
    }

    public String getSpecializare() {
        return specializare;
    }
}

