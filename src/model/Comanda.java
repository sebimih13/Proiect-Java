package model;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class Comanda {
    public enum Status {
        InPregatire,
        Livrata
    }

    public static Integer maxIDComanda;

    private Integer ID;
    private Status status;
    private java.sql.Date data;
    private java.sql.Time ora;
    private List<Produs> produse;
    private Map<Produs, Integer> cantitati;

    private Client client;

    static {
        maxIDComanda = 0;
    }

    public Comanda(Integer ID) {
        this.ID = ID;
        this.status = Status.InPregatire;
        this.data = java.sql.Date.valueOf(LocalDate.now());
        this.ora = java.sql.Time.valueOf(LocalTime.now());
        this.produse = new ArrayList<>();
        this.cantitati = new HashMap<>();
    }

    public Integer getID() {
        return ID;
    }

    public Status getStatus() {
        return status;
    }

    public Date getData() {
        return data;
    }

    public Time getOra() {
        return ora;
    }

    public List<Produs> getProduse() {
        return produse;
    }

    public Map<Produs, Integer> getCantitati() {
        return cantitati;
    }

    public void adaugaProdus(Produs produs, Integer cantitate) {
        produse.add(produs);
        cantitati.put(produs, cantitate);
    }

    public void editComanda() {
        // TODO
    }

    @Override
    public String toString() {
        // TODO: client poate sa fie si null
        return "plasata: " + data + " " + ora;
    }
}

