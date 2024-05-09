package model;

public abstract class Angajat {
    private Integer ID;

    private String username;
    private String password;

    private String nume;
    private String prenume;
    private int salariu;
    private String nrTelefon;

    private Angajat manager;
    private Restaurant restaurant;

    protected static Integer maxIDAngajat;

    static {
        maxIDAngajat = 1;
    }

    public Angajat(Integer ID, String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.salariu = salariu;
        this.nrTelefon = nrTelefon;
        this.manager = manager;
        this.restaurant = restaurant;

        maxIDAngajat = Integer.max(maxIDAngajat, ID);
    }

    public Integer getID() {
        return ID;
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

