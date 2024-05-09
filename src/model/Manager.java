package model;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

import App.Database;

public class Manager extends Angajat {
    private String nivelEducatie;
    private ArrayList<Angajat> subordonati;

    private final Scanner scanner;

    public Manager(Integer ID, String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String nivelEducatie) {
        super(ID, username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.nivelEducatie = nivelEducatie;
        subordonati = new ArrayList<>();

        scanner = new Scanner(System.in);
    }

    public String getNivelEducatie() {
        return nivelEducatie;
    }

    @Override
    public final void menu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\nAlegeti o optiune:");
            System.out.println("1. Afisare subordonati");
            System.out.println("2. Adaugare angajat nou in restaurant");
            System.out.println("3. Sterge angajat din restaurant");
            System.out.println("4. Modifica datele unui angajat");
            System.out.println("5. Log out");

            System.out.print("Optiune: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                scanner.next();
                continue;
            }

            int option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    System.out.println("\nSubordonati:");
                    for (int i = 0; i < subordonati.size(); i++) {
                        System.out.println(subordonati.get(i));
                    }
                    break;

                case 2:
                    try {
                        Angajat subordonatNou = adaugareAngajatNouMenu();
                        Database.getInstance().addAngajat(subordonatNou);
                        addSubordonat(subordonatNou);
                    }
                    catch (SQLException e) {
                        System.out.println("FAILED -> adaugare angajat nou");
                        e.printStackTrace();
                    }
                    break;

                case 3:
                    deleteSubodonat();
                    break;

                case 4:
                    // TODO
                    break;

                case 5:
                    logout = true;
                    break;
            }
        }
    }

    public void addSubordonat(Angajat subordonat) {
        subordonati.add(subordonat);
    }

    private Angajat adaugareAngajatNouMenu() {
        int option = 0;
        while (true) {
            System.out.println("\nSelectati pozitia:");
            System.out.println("1. Ospatar");
            System.out.println("2. Sef Bucatar");
            System.out.println("3. Barman");

            System.out.print("Optiune: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                scanner.next();
                continue;
            }

            option = scanner.nextInt();
            scanner.nextLine();

            if (option < 1 || 3 < option) {
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                continue;
            }

            break;
        }

        String username = null;
        while (true) {
            System.out.print("username: ");
            username = scanner.nextLine();
            if (username.isEmpty()) {
                System.out.println("username trebuie sa contina cel putin un caracter!");
                continue;
            }
            // TODO: verificare sa nu existe deja in baza de date
            break;
        }

        String password = null;
        while (true) {
            System.out.print("password: ");
            password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("password trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        String nume = null;
        while (true) {
            System.out.print("nume: ");
            nume = scanner.nextLine();
            if (nume.isEmpty()) {
                System.out.println("numele trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        String prenume = null;
        while (true) {
            System.out.print("prenume: ");
            prenume = scanner.nextLine();
            if (prenume.isEmpty()) {
                System.out.println("prenumele trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        Integer salariu = null;
        while (true) {
            System.out.print("salariu: ");
            if (!scanner.hasNextInt()) {
                System.out.println("salariul trebuie sa fie un numar intreg!");
                scanner.next();
                continue;
            }
            salariu = scanner.nextInt();
            scanner.nextLine();
            break;
        }

        String nrTelefon = null;
        while (true) {
            System.out.print("numar telefon: ");
            nrTelefon = scanner.nextLine();
            if (nrTelefon.length() != 10 || !nrTelefon.matches("[0-9]+")) {
                System.out.println("numarul de telefon trebuie sa contina fix 10 cifre!");
                continue;
            }
            // TODO: verificare sa nu existe deja in baza de date
            break;
        }

        switch (option) {
            case 1: {
                String nivelEngleza = null;
                while (true) {
                    System.out.print("nivel engleza: ");
                    nivelEngleza = scanner.nextLine();
                    if (nivelEngleza.isEmpty()) {
                        System.out.println("nivelul de engleza trebuie fie una dintre urmatoarele variante: A1, A2, B1, B2, C1, C2!");
                        continue;
                    }
                    // TODO: adauga verificare daca e A1, A2, B1, B2, C1, C2 => in baza de date
                    break;
                }

                return new Ospatar(++maxIDAngajat, username, password, nume, prenume, salariu, nrTelefon, this, this.getRestaurant(), nivelEngleza);
            }

            case 2: {
                String specializare = null;
                while (true) {
                    System.out.print("specializare: ");
                    specializare = scanner.nextLine();
                    if (specializare.isEmpty()) {
                        System.out.println("specializarea trebuie sa contina cel putin un caracter!");
                        continue;
                    }
                    break;
                }

                return new SefBucatar(++maxIDAngajat, username, password, nume, prenume, salariu, nrTelefon, this, this.getRestaurant(), specializare);
            }

            case 3: {
                String specializare = null;
                while (true) {
                    System.out.print("specializare: ");
                    specializare = scanner.nextLine();
                    if (specializare.isEmpty()) {
                        System.out.println("specializarea trebuie sa contina cel putin un caracter!");
                        continue;
                    }
                    break;
                }

                return new Barman(++maxIDAngajat, username, password, nume, prenume, salariu, nrTelefon, this, this.getRestaurant(), specializare);
            }
        }

        return null;
    }

    private void deleteSubodonat() {
        System.out.println("\nAlegeti subordonatul:");

        for (int i = 0; i < subordonati.size(); i++) {
            System.out.println(subordonati.get(i).getID() + " -> " + subordonati.get(i));
        }

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < subordonati.size(); i++) {
            if (subordonati.get(i).getID() == option) {
                try {
                    Database.getInstance().deleteAngajat(option);
                    subordonati.remove(i);
                    System.out.println("Angajatul a fost sters din baza de date");
                    return;
                }
                catch (SQLException e) {
                    System.out.println("FAILED -> deleteSubordonat()");
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
    }
}

