package model;

public class Bautura extends Produs {
    private Integer litri;

    public Bautura(Integer ID, String nume, String descriere, Integer pret, Integer litri) {
        super(ID, nume, descriere, pret);
        this.litri = litri;
    }

    public Integer getLitri() {
        return litri;
    }

    @Override
    public String toString() {
        // TODO
        return super.toString();
    }
}

