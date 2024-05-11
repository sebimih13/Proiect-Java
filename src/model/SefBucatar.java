package model;

import App.Database;

import java.sql.SQLException;
import java.util.ArrayList;

public class SefBucatar extends Angajat {
    String specializare;
    private ArrayList<Preparat> preparate;

    public SefBucatar(Integer ID, String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String specializare) {
        super(ID, username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.specializare = specializare;
        this.preparate = new ArrayList<>();
    }

    public String getSpecializare() {
        return specializare;
    }

    @Override
    public final void menu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\nAlegeti o optiune:");
            System.out.println("1. Afisare preparate");
            System.out.println("2. Adauga un nou preparat");
            System.out.println("3. Sterge un preparat");
            System.out.println("4. Editare preparat");
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
                    afisarePreparateMenu();
                    break;

                case 2:
                    adaugarePreparatMenu();
                    break;

                case 3:
                    stergePreparatMenu();
                    break;

                case 4:
                    editarePreparat();
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

    public void addPreparat(Preparat preparat) {
        preparate.add(preparat);
    }

    // TODO: alta functie -> afisareDate()
    @Override
    public String toString() {
        return "Sef Bucatar: " + super.toString();
    }

    @Override
    public void editareDatePersonaleMenu() {
        System.out.println("\nAlegeti o optiune:");
        System.out.println("1. Editare username");
        System.out.println("2. Editare password");
        System.out.println("3. Editare nume");
        System.out.println("4. Editare prenume");
        System.out.println("5. Editare numar telefon");
        System.out.println("6. Editare specializare");

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
                editSpecializare();
                break;

            default:
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                break;
        }
    }

    private void editSpecializare() {
        String specializareNou = null;
        while (true) {
            System.out.print("specializare: ");

            specializareNou = scanner.nextLine();
            if (specializareNou.isEmpty()) {
                System.out.println("specializarea trebuie sa contina cel putin un caracter!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("angajat", "id_angajat", "specializare", specializareNou, this.getID());
            this.specializare = specializareNou;
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editSpecializare()");
            e.printStackTrace();
        }
    }

    private void afisarePreparateMenu() {
        System.out.println("\nPreparate:");
        for (int i = 0; i < preparate.size(); i++) {
            System.out.println(preparate.get(i) + "\n");
        }
    }

    private void adaugarePreparatMenu() {
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

        String descriere = null;
        while (true) {
            System.out.print("descriere: ");
            descriere = scanner.nextLine();
            if (descriere.isEmpty()) {
                System.out.println("descrierea trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        Integer pret = null;
        while (true) {
            System.out.print("pret: ");
            if (!scanner.hasNextInt()) {
                System.out.println("pretul trebuie sa fie un numar natural strict pozitiv!");
                scanner.next();
                continue;
            }
            pret = scanner.nextInt();
            scanner.nextLine();
            if (pret <= 0) {
                System.out.println("pretul trebuie sa fie un numar natural strict pozitiv!");
                continue;
            }
            break;
        }

        Integer grame = null;
        while (true) {
            System.out.print("grame: ");
            if (!scanner.hasNextInt()) {
                System.out.println("cantitatea trebuie sa fie un numar natural strict pozitiv!");
                scanner.next();
                continue;
            }
            grame = scanner.nextInt();
            scanner.nextLine();
            if (grame <= 0) {
                System.out.println("cantitatea trebuie sa fie un numar natural strict pozitiv!");
                continue;
            }
            break;
        }

        try {
            Preparat preparatNou = new Preparat(++Preparat.maxIDProdus, nume, descriere, pret, grame);
            Database.getInstance().addProdus(preparatNou, this);
            addPreparat(preparatNou);
        }
        catch (SQLException e) {
            System.out.println("FAILED -> adaugare preparat nou");
            e.printStackTrace();
        }
    }

    private void stergePreparatMenu() {
        System.out.println("\nAlegeti produsul:");

        for (int i = 0; i < preparate.size(); i++) {
            System.out.println(preparate.get(i).getID() + " -> " + preparate.get(i));
        }

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < preparate.size(); i++) {
            if (preparate.get(i).getID() == option) {
                try {
                    Database.getInstance().deleteProdus(option);
                    preparate.remove(i);
                    System.out.println("Preparatul a fost sters din baza de date");
                    return;
                }
                catch (SQLException e) {
                    System.out.println("FAILED -> stergePreparatMenu()");
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
    }

    private void editarePreparat() {
        // TODO
    }
}

