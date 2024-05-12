package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Restaurant {
    private static Integer maxIDRestaurant;

    private Integer ID;

    private String nume;
    private Integer nrStele;
    private String oras;
    private String strada;
    private String nrTelefon;

    private ArrayList<Angajat> angajati;
    private ArrayList<Comanda> comenzi;

    static {
        maxIDRestaurant = 0;
    }

    public Restaurant(Integer ID, String nume, Integer nrStele, String oras, String strada, String nrTelefon) {
        this.ID = ID;
        this.nume = nume;
        this.nrStele = nrStele;
        this.oras = oras;
        this.strada = strada;
        this.nrTelefon = nrTelefon;
        this.comenzi = new ArrayList<>();
        this.angajati = new ArrayList<>();

        maxIDRestaurant = Integer.max(maxIDRestaurant, ID);
    }

    public Integer getID() {
        return ID;
    }

    public String getNume() {
        return nume;
    }

    public Integer getNrStele() {
        return nrStele;
    }

    public String getOras() {
        return oras;
    }

    public String getStrada() {
        return strada;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public ArrayList<Angajat> getAngajati() {
        return angajati;
    }

    public ArrayList<Comanda> getComenzi() {
        return comenzi;
    }

    public void addComanda(Comanda comanda) {
        comenzi.add(comanda);
    }

    public void deleteComanda(Comanda comanda) {
        comenzi.remove(comanda);
    }

    public void addAngajat(Angajat angajat) {
        angajati.add(angajat);
    }

    public Set<Produs> getProduse() {
        Set<Produs> produse = new HashSet<>();

        for (int i = 0; i < angajati.size(); i++) {
            if (angajati.get(i) instanceof SefBucatar) {
                for (Preparat produs : ((SefBucatar) angajati.get(i)).getPreparate()) {
                    produse.add(produs); // TODO: adauga o copie / clona
                }
            }
            else if (angajati.get(i) instanceof Barman) {
                for (Bautura produs : ((Barman) angajati.get(i)).getBauturi()) {
                    produse.add(produs); // TODO: adauga o copie / clona
                }
            }
        }

        return produse;
    }

    @Override
    public String toString() {
        return this.ID + ". " + this.nume + "\n"
             + "stele: " + this.nrStele + "\n"
             + "oras: " + this.oras + "\n"
             + "strada: " + this.strada + "\n";
    }
}

