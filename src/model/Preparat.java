package model;

public class Preparat extends Produs {
    Integer grame;

    public Preparat(Integer ID, String nume, String descriere, Integer pret, Integer grame) {
        super(ID, nume, descriere, pret);
        this.grame = grame;
    }

    public Integer getGrame() {
        return grame;
    }

    @Override
    public String toString() {
        // TODO
        return super.toString();
    }
}

