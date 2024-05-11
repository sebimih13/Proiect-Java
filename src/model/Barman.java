package model;

import java.util.ArrayList;

public class Barman extends Angajat {
    String specializare;
    private ArrayList<Bautura> bauturi;

    public Barman(Integer ID, String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String specializare) {
        super(ID, username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.specializare = specializare;
        this.bauturi = new ArrayList<>();
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

    public void addBautura(Bautura bautura) {
        bauturi.add(bautura);
    }

    // TODO: alta functie -> afisareDate()
    @Override
    public String toString() {
        return "Barman: " + super.toString();
    }

    @Override
    public void editareDatePersonaleMenu() {
        // TODO
    }
}

