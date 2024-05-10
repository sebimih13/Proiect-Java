package model;

import App.Database;

import java.sql.SQLException;

public class Ospatar extends Angajat {
    String nivelEngleza;

    public Ospatar(Integer ID, String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String nivelEngleza) {
        super(ID, username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.nivelEngleza = nivelEngleza;
    }

    public String getNivelEngleza() {
        return nivelEngleza;
    }

    @Override
    public final void menu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\nAlegeti o optiune:");
            System.out.println("1. Adauga comanda");
            System.out.println("2. Schimba status comanda");
            System.out.println("3. Editare date personale");
            // TODO: Sterge comanda
            // TODO: modificare comanda
            System.out.println("4. Log out");

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
                    adaugareComandaMenu();
                    break;

                case 2:
                    // TODO
                    break;

                case 3:
                    editareDatePersonaleMenu();
                    break;

                case 4:
                    logout = true;
                    break;

                default:
                    System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                    break;
            }
        }
    }

    // TODO: alta functie -> afisareDate()
    @Override
    public String toString() {
        return "Ospatar: " + super.toString();
    }

    @Override
    public void editareDatePersonaleMenu() {
        System.out.println("\nAlegeti o optiune:");
        System.out.println("1. Editare username");
        System.out.println("2. Editare password");
        System.out.println("3. Editare nume");
        System.out.println("4. Editare prenume");
        System.out.println("5. Editare numar telefon");
        System.out.println("6. Editare nivel engleza");

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
                editNivelEngleza();
                break;

            default:
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                break;
        }
    }

    private void editNivelEngleza() {
        String nivelEnglezaNou = null;
        while (true) {
            System.out.print("nivel engleza: ");

            nivelEnglezaNou = scanner.nextLine();
            if (nivelEnglezaNou.isEmpty()) {
                System.out.println("nivel engeleza trebuie sa contina cel putin un caracter!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("angajat", "id_angajat", "nivel_engleza", nivelEnglezaNou, this.getID());
            this.nivelEngleza = nivelEnglezaNou;
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editNivelEngleza()");
            e.printStackTrace();
        }
    }

    private void adaugareComandaMenu() {
        // TODO
    }
}

