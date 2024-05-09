package model;

public class Ospatar extends Angajat {
    String nivelEngleza;

    public Ospatar(Integer ID, String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String nivelEngleza) {
        super(ID, username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.nivelEngleza = nivelEngleza;
    }

    public String getNivelEngleza() {
        return nivelEngleza;
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
        return "Ospatar: " + super.toString();
    }
}

