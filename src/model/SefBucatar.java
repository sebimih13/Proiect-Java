package model;

public class SefBucatar extends Angajat {
    String specializare;

    public SefBucatar(Integer ID, String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String specializare) {
        super(ID, username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.specializare = specializare;
    }

    public String getSpecializare() {
        return specializare;
    }

    @Override
    public final void menu() {
        while (true) {
            // TODO
        }
    }

    // TODO: alta functie -> afisareDate()
    @Override
    public String toString() {
        return "Sef Bucatar: " + super.toString();
    }
}

