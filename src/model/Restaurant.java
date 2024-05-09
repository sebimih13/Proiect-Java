package model;

public class Restaurant {
    private Integer ID;

    private String nume;
    private Integer nrStele;
    private String oras;
    private String strada;
    private String nrTelefon;

    private static Integer maxIDRestaurant;

    static {
        maxIDRestaurant = Integer.MIN_VALUE;
    }

    public Restaurant(Integer ID, String nume, Integer nrStele, String oras, String strada, String nrTelefon) {
        this.ID = ID;
        this.nume = nume;
        this.nrStele = nrStele;
        this.oras = oras;
        this.strada = strada;
        this.nrTelefon = nrTelefon;

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
}

