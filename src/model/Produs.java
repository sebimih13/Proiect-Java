package model;

public class Produs {
    private Integer ID;

    String nume;
    String descriere;
    Integer pret;

    protected static Integer maxIDProdus;

    static {
        maxIDProdus = 0;
    }

    public Produs(Integer ID, String nume, String descriere, Integer pret) {
        this.ID = ID;
        this.nume = nume;
        this.descriere = descriere;
        this.pret = pret;

        maxIDProdus = Integer.max(maxIDProdus, ID);
    }

    public Integer getID() {
        return ID;
    }

    public String getNume() {
        return nume;
    }

    public String getDescriere() {
        return descriere;
    }

    public Integer getPret() {
        return pret;
    }

    @Override
    public String toString() {
        return "Produs: " + nume + "\n"
             + "Descriere: " + descriere + "\n"
             + "Pret: " + pret;
    }
}

