package model;

public class Bautura extends Produs {
    private Integer ml;

    public Bautura(Integer ID, String nume, String descriere, Integer pret, Integer ml) {
        super(ID, nume, descriere, pret);
        this.ml = ml;
    }

    public Integer getMl() {
        return ml;
    }

    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Ml: " + ml;
    }
}

