package model;

public abstract class Angajat {
    protected String username;
    protected String password;

    protected String nume;
    protected String prenume;
    protected int salariu;
    protected String nrTelefon;

    protected Angajat manager;
    protected Restaurant restaurant;

    public Angajat(String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant) {
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.salariu = salariu;
        this.nrTelefon = nrTelefon;
        this.manager = manager;
        this.restaurant = restaurant;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getNume() {
        return nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public int getSalariu() {
        return salariu;
    }

    public String getNrTelefon() {
        return nrTelefon;
    }

    public Angajat getManager() {
        return manager;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public abstract void menu();

    // TODO: alta functie -> afisareDate()
    @Override
    public String toString() {
        return nume + " " + prenume + " " + nrTelefon;
    }
}

