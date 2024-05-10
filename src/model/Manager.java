package model;

import java.sql.SQLException;
import java.util.Scanner;
import java.util.ArrayList;

import App.Database;

public class Manager extends Angajat {
    private String nivelEducatie;
    private ArrayList<Angajat> subordonati;

    public Manager(Integer ID, String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String nivelEducatie) {
        super(ID, username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.nivelEducatie = nivelEducatie;
        this.subordonati = new ArrayList<>();
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
            System.out.println("4. Modifica salariul unui angajat");
            System.out.println("5. Editare date personale");
            System.out.println("6. Log out");

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
                    modificaSalariuSubordonat();
                    break;

                case 5:
                    editareDatePersonaleMenu();
                    break;

                case 6:
                    logout = true;
                    break;

                default:
                    System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
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
                System.out.println("salariul trebuie sa fie un numar natural strict pozitiv!");
                scanner.next();
                continue;
            }
            salariu = scanner.nextInt();
            scanner.nextLine();
            if (salariu <= 0) {
                System.out.println("salariul trebuie sa fie un numar natural strict pozitiv!");
                continue;
            }
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

    public void modificaSalariuSubordonat() {
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

        int optionSubordonat = scanner.nextInt();
        scanner.nextLine();

        Angajat subordonat = null;
        for (int i = 0; i < subordonati.size(); i++) {
            if (subordonati.get(i).getID() == optionSubordonat) {
                subordonat = subordonati.get(i);
            }
        }

        if (subordonat == null) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            return;
        }

        subordonat.editSalariu();
        System.out.println("Salariul a fost modificat!");
    }

    @Override
    public void editareDatePersonaleMenu() {
        System.out.println("\nAlegeti o optiune:");
        System.out.println("1. Editare username");
        System.out.println("2. Editare password");
        System.out.println("3. Editare nume");
        System.out.println("4. Editare prenume");
        System.out.println("5. Editare numar telefon");
        System.out.println("6. Editare studii");

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        switch (option) {
            case 1:
                editUsername();
                break;

            case 2:
                editPassword();
                break;

            case 3:
                editNume();
                break;

            case 4:
                editPrenume();
                break;

            case 5:
                editNrTelefon();
                break;

            case 6:
                editNivelEducatie();
                break;

            default:
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                break;
        }
    }

    public void editNivelEducatie() {
        String nivelEducatieNou = null;
        while (true) {
            System.out.print("nivel educatie: ");

            nivelEducatieNou = scanner.nextLine();
            if (nivelEducatieNou.isEmpty()) {
                System.out.println("nivel educatie trebuie sa contina cel putin un caracter!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("angajat", "id_angajat", "nivel_educatie", nivelEducatieNou, this.getID());
            this.nivelEducatie = nivelEducatieNou;
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editNivelEducatie()");
            e.printStackTrace();
        }
    }
}

