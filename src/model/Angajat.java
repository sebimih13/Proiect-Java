package model;

import App.Database;

import java.sql.SQLException;
import java.util.Scanner;

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

    protected final Scanner scanner;

    static {
        maxIDAngajat = 0;
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
        scanner = new Scanner(System.in);
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

    public abstract void editareDatePersonaleMenu();

    // TODO: alta functie -> afisareDate()
    @Override
    public String toString() {
        return nume + " " + prenume + " " + nrTelefon + " -> " + salariu + " LEI";
    }

    public void editUsername() {
        String usernameNou = null;
        while (true) {
            System.out.print("username: ");

            usernameNou = scanner.nextLine();
            if (usernameNou.isEmpty()) {
                System.out.println("username trebuie sa contina cel putin un caracter!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("angajat", "id_angajat", "username", usernameNou, this.ID);
            this.username = usernameNou;
            System.out.println("username a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editUsername()");
            e.printStackTrace();
        }
    }

    public void editPassword() {
        String passwordNou = null;
        while (true) {
            System.out.print("password: ");

            passwordNou = scanner.nextLine();
            if (passwordNou.isEmpty()) {
                System.out.println("password trebuie sa contina cel putin un caracter!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("angajat", "id_angajat", "password", passwordNou, this.ID);
            this.username = passwordNou;
            System.out.println("password a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editPassword()");
            e.printStackTrace();
        }
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
            Database.getInstance().editStringValue("angajat", "id_angajat", "nume", numeNou, this.ID);
            this.nume = numeNou;
            System.out.println("numele a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editNume()");
            e.printStackTrace();
        }
    }

    public void editPrenume() {
        String prenumeNou = null;
        while (true) {
            System.out.print("prenume: ");

            prenumeNou = scanner.nextLine();
            if (prenumeNou.isEmpty()) {
                System.out.println("prenumele trebuie sa contina cel putin un caracter!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("angajat", "id_angajat", "prenume", prenumeNou, this.ID);
            this.prenume = prenumeNou;
            System.out.println("prenumele a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editPrenume()");
            e.printStackTrace();
        }
    }

    public void editSalariu() {
        Integer salariuNou = null;
        while (true) {
            System.out.print("salariu: ");

            if (!scanner.hasNextInt()) {
                System.out.println("salariul trebuie sa fie un numar intreg!");
                scanner.next();
                continue;
            }

            salariuNou = scanner.nextInt();
            scanner.nextLine();
            break;
        }

        try {
            Database.getInstance().editIntValue("angajat", "id_angajat", "salariu", salariuNou, this.ID);
            this.salariu = salariuNou;
            System.out.println("salariul a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editSalariu()");
            e.printStackTrace();
        }
    }

    public void editNrTelefon() {
        String nrTelefonNou = null;
        while (true) {
            System.out.print("numar telefon: ");

            nrTelefonNou = scanner.nextLine();
            if (nrTelefon.length() != 10 || !nrTelefon.matches("[0-9]+")) {
                System.out.println("numarul de telefon trebuie sa contina fix 10 cifre!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("angajat", "id_angajat", "nr_telefon", nrTelefonNou, this.ID);
            this.nrTelefon = nrTelefonNou;
            System.out.println("numarul de telefon a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editNrTelefon()");
            e.printStackTrace();
        }
    }
}

