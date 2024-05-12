package model;

import App.Database;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            System.out.println("2. Sterge comanda");
            System.out.println("3. Editare comanda");
            System.out.println("4. Finalizare comanda");
            System.out.println("5. Afisare date personale");
            System.out.println("6. Editare date personale");
            System.out.println("7. Log out");

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
                    stergeComandaMenu();
                    break;

                case 3:
                    editareComandaMenu();
                    break;

                case 4:
                    finalizareComanda();
                    break;

                case 5:
                    afisareDatePersonaleMenu();
                    break;

                case 6:
                    editareDatePersonaleMenu();
                    break;

                case 7:
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

    @Override
    public void afisareDatePersonaleMenu() {
        super.afisareDatePersonaleMenu();
        System.out.println("Nivel engleza: " + this.nivelEngleza);
    }

    private void adaugareComandaMenu() {
        Comanda comandaNoua = new Comanda(++Comanda.maxIDComanda, getRestaurant());

        List<Produs> produse = new ArrayList<>(getRestaurant().getProduse());

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

            comandaNoua.adaugaProdus(produse.get(option - 1), cantitate);
        }

        try {
            getRestaurant().addComanda(comandaNoua);
            Database.getInstance().addComanda(comandaNoua, null);
        }
        catch (SQLException e) {
            System.out.println("FAILED -> adaugareComandaMenu()");
            e.printStackTrace();
        }
    }

    private void stergeComandaMenu() {
        List<Comanda> comenzi = new ArrayList<>();

        Integer index = 0;
        for (Comanda comanda : getRestaurant().getComenzi()) {
            if (comanda.getStatus() == Comanda.Status.InPregatire) {
                System.out.println(++index + ". " + comanda);
                comenzi.add(comanda);
            }
        }

        if (comenzi.size() == 0) {
            System.out.println("Nu exista comenzi in pregatire!");
            return;
        }

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        if (option < 1 || comenzi.size() + 1 < option) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            return;
        }

        try {
            Database.getInstance().deleteComanda(comenzi.get(option - 1).getID());
            getRestaurant().deleteComanda(comenzi.get(option - 1));
        }
        catch (SQLException e) {
            System.out.println("FAILED -> stergeComandaMenu()");
            e.printStackTrace();
        }
    }

    private void editareComandaMenu() {
        List<Comanda> comenzi = new ArrayList<>();

        Integer index = 0;
        for (Comanda comanda : getRestaurant().getComenzi()) {
            if (comanda.getStatus() == Comanda.Status.InPregatire) {
                System.out.println(++index + ". " + comanda);
                comenzi.add(comanda);
            }
        }

        if (comenzi.size() == 0) {
            System.out.println("Nu exista comenzi in pregatire!");
            return;
        }

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        if (option < 1 || comenzi.size() + 1 < option) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            return;
        }

        System.out.println("Alegeti o actiune:");
        System.out.println("1. Schimba cantitatea");
        System.out.println("2. Adauga produse noi la comanda");
        System.out.println("3. Sterge un produs");
        System.out.print("Optiune: ");
        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int actiune = scanner.nextInt();
        scanner.nextLine();

        switch (actiune) {
            case 1:
                comenzi.get(option - 1).schimbaCantitateaMenu();
                break;

            case 2:
                comenzi.get(option - 1).addProdusMenu();
                break;

            case 3:
                comenzi.get(option - 1).deleteProdusMenu();
                break;

            default:
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                break;
        }
    }

    private void finalizareComanda() {
        List<Comanda> comenzi = new ArrayList<>();

        Integer index = 0;
        for (Comanda comanda : getRestaurant().getComenzi()) {
            if (comanda.getStatus() == Comanda.Status.InPregatire) {
                System.out.println(++index + ". " + comanda);
                comenzi.add(comanda);
            }
        }

        if (comenzi.size() == 0) {
            System.out.println("Nu exista comenzi in pregatire!");
            return;
        }

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        if (option < 1 || comenzi.size() + 1 < option) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            return;
        }

        try {
            Database.getInstance().editStringValue("comanda", "id_comanda", "status", Comanda.Status.Livrata.toString(), comenzi.get(option - 1).getID());
            comenzi.get(option - 1).finalizareComanda();
        }
        catch (SQLException e) {
            System.out.println("FAILED -> finalizareComanda()");
            e.printStackTrace();
        }
    }
}

