package model;

import App.Database;

import java.sql.SQLException;
import java.util.Scanner;

public class Produs {
    private Integer ID;

    String nume;
    String descriere;
    Integer pret;

    protected static Integer maxIDProdus;

    protected final Scanner scanner;

    static {
        maxIDProdus = 0;
    }

    public Produs(Integer ID, String nume, String descriere, Integer pret) {
        this.ID = ID;
        this.nume = nume;
        this.descriere = descriere;
        this.pret = pret;

        maxIDProdus = Integer.max(maxIDProdus, ID);
        scanner = new Scanner(System.in);
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

    public void editNume() {
        String numeNou = null;
        while (true) {
            System.out.print("nume: ");

            numeNou = scanner.nextLine();
            if (numeNou.isEmpty()) {
                System.out.println("numele trebuie sa contina cel putin un caracter!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("produs", "id_produs", "nume", numeNou, this.getID());
            this.nume = numeNou;
            System.out.println("numele a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editNume()");
            e.printStackTrace();
        }
    }

    public void editDescriere() {
        String descriereNou = null;
        while (true) {
            System.out.print("descriere: ");

            descriereNou = scanner.nextLine();
            if (descriereNou.isEmpty()) {
                System.out.println("descrierea trebuie sa contina cel putin un caracter!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("produs", "id_produs", "descriere", descriereNou, this.getID());
            this.descriere = descriereNou;
            System.out.println("descrierea a fost modificata cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editDescriere()");
            e.printStackTrace();
        }
    }

    public void editPret() {
        Integer pretNou = null;
        while (true) {
            System.out.print("pret: ");

            if (!scanner.hasNextInt()) {
                System.out.println("pretul trebuie sa fie un numar natural strict pozitiv!");
                scanner.next();
                continue;
            }

            pretNou = scanner.nextInt();
            scanner.nextLine();

            if (pretNou <= 0) {
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editIntValue("produs", "id_produs", "pret", pretNou, this.ID);
            this.pret = pretNou;
            System.out.println("pretul a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editPret()");
            e.printStackTrace();
        }
    }
}

