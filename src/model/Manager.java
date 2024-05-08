package model;

public class Manager extends Angajat {
    private String nivelEducatie;

    public Manager(String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String nivelEducatie) {
        super(username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.nivelEducatie = nivelEducatie;
    }

    public String getNivelEducatie() {
        return nivelEducatie;
    }
}

