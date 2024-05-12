package model;

import App.Database;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
    private Restaurant restaurant;

    private List<Produs> produse;
    private Map<Produs, Integer> cantitati;

    protected static final Scanner scanner;

    static {
        maxIDComanda = 0;
        scanner = new Scanner(System.in);
    }

    public Comanda(Integer ID, Status status, Date data, Time ora, Restaurant restaurant) {
        this.ID = ID;
        this.status = status;
        this.data = data;
        this.ora = ora;
        this.restaurant = restaurant;
        this.produse = new ArrayList<>();
        this.cantitati = new HashMap<>();

        maxIDComanda = Integer.max(maxIDComanda, ID);
    }

    public Comanda(Integer ID, Restaurant restaurant) {
        this.ID = ID;
        this.status = Status.InPregatire;
        this.data = java.sql.Date.valueOf(LocalDate.now());
        this.ora = java.sql.Time.valueOf(LocalTime.now());
        this.restaurant = restaurant;
        this.produse = new ArrayList<>();
        this.cantitati = new HashMap<>();

        maxIDComanda = Integer.max(maxIDComanda, ID);
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

    public Restaurant getRestaurant() {
        return restaurant;
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

    public void finalizareComanda() {
        status = Status.Livrata;
    }

    @Override
    public String toString() {
        String rezumatComanda = "plasata: " + data + " " + ora + " " + status.toString() + "\n";

        for (Produs produs : produse) {
            rezumatComanda += produs.getNume() + " x" + cantitati.get(produs) + "\n";
        }

        return rezumatComanda;
    }

    public void schimbaCantitateaMenu() {
        if (produse.isEmpty()) {
            System.out.println("Nu exista produse in comanda!");
            return;
        }

        System.out.println("Alege un produs:");
        for (int i = 0; i < produse.size(); i++) {
            System.out.println((i + 1) + ". " + produse.get(i).getNume() + " x" + cantitati.get(produse.get(i)));
        }

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        if (option < 1 || produse.size() + 1 < option) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            return;
        }

        System.out.print("Cantitate noua: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int cantitateNoua = scanner.nextInt();
        scanner.nextLine();

        if (cantitateNoua <= 0) {
            System.out.println("Optiune invalida! Alegeti un numar natural strict pozitiv!");
            return;
        }

        try {
            Database.getInstance().editContine(cantitateNoua, produse.get(option - 1).getID(), getID());
        }
        catch (SQLException e) {
            System.out.println("FAILED -> schimbaCantitateaMenu()");
            e.printStackTrace();
        }
    }

    public void addProdusMenu() {
        List<Produs> produse = new ArrayList<>(restaurant.getProduse());

        boolean comandaIncheiata = false;
        while (!comandaIncheiata) {
            System.out.println("\nAlege produsul:");
            for (int i = 0; i < produse.size(); i++) {
                System.out.printf((i + 1) + ". " + produse.get(i) + "\n");
            }
            System.out.println((produse.size() + 1) + ". Incheie comanda");

            System.out.print("Optiune: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                scanner.next();
                continue;
            }

            int option = scanner.nextInt();
            scanner.nextLine();

            if (option < 1 || produse.size() + 1 < option) {
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                continue;
            }

            if (option == produse.size() + 1) {
                comandaIncheiata = true;
                continue;
            }

            Integer cantitate = null;
            while (true) {
                System.out.print("Cantitate: ");

                if (!scanner.hasNextInt()) {
                    System.out.println("Optiune invalida! Alegeti un numar din natural strict pozitiv!");
                    scanner.next();
                    continue;
                }

                cantitate = scanner.nextInt();
                scanner.nextLine();

                if (cantitate <= 0) {
                    System.out.println("Optiune invalida! Alegeti un numar din natural strict pozitiv!");
                    continue;
                }

                break;
            }

            try {
                this.adaugaProdus(produse.get(option - 1), cantitate);
                Database.getInstance().addContine(produse.get(option - 1), this);
            }
            catch (SQLException e) {
                System.out.println("FAILED -> addProdusMenu()");
                e.printStackTrace();
            }
        }
    }

    public void deleteProdusMenu() {
        if (produse.isEmpty()) {
            System.out.println("Nu exista produse in comanda!");
            return;
        }

        System.out.println("Alege un produs:");
        for (int i = 0; i < produse.size(); i++) {
            System.out.println((i + 1) + ". " + produse.get(i).getNume() + " x" + cantitati.get(produse.get(i)));
        }

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        if (option < 1 || produse.size() + 1 < option) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            return;
        }

        try {
            Database.getInstance().deleteContine(produse.get(option - 1), this);
            this.produse.remove(produse.get(option - 1));
        }
        catch (SQLException e) {
            System.out.println("FAILED -> deleteProdusMenu()");
            e.printStackTrace();
        }
    }
}

