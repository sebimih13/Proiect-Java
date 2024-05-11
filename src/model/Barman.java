package model;

import App.Database;

import java.sql.SQLException;
import java.util.ArrayList;

public class Barman extends Angajat {
    String specializare;
    private ArrayList<Bautura> bauturi;

    public Barman(Integer ID, String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String specializare) {
        super(ID, username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.specializare = specializare;
        this.bauturi = new ArrayList<>();
    }

    public String getSpecializare() {
        return specializare;
    }

    @Override
    public final void menu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\nAlegeti o optiune:");
            System.out.println("1. Afisare bauturi");
            System.out.println("2. Adauga o noua bautura");
            System.out.println("3. Sterge o bautura");
            System.out.println("4. Editare bautura");
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
                    afisareBauturiMenu();
                    break;

                case 2:
                    adaugareBauturaMenu();
                    break;

                case 3:
                    stergeBauturaMenu();
                    break;

                case 4:
                    editareBautura();
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

    public void addBautura(Bautura bautura) {
        bauturi.add(bautura);
    }

    // TODO: alta functie -> afisareDate()
    @Override
    public String toString() {
        return "Barman: " + super.toString();
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

    private void afisareBauturiMenu() {
        System.out.println("\nBauturi:");
        for (int i = 0; i < bauturi.size(); i++) {
            System.out.println(bauturi.get(i) + "\n");
        }
    }

    private void adaugareBauturaMenu() {
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

        Integer ml = null;
        while (true) {
            System.out.print("ml: ");
            if (!scanner.hasNextInt()) {
                System.out.println("cantitatea trebuie sa fie un numar natural strict pozitiv!");
                scanner.next();
                continue;
            }
            ml = scanner.nextInt();
            scanner.nextLine();
            if (ml <= 0) {
                System.out.println("cantitatea trebuie sa fie un numar natural strict pozitiv!");
                continue;
            }
            break;
        }

        try {
            Bautura bauturaNou = new Bautura(++Bautura.maxIDProdus, nume, descriere, pret, ml);
            Database.getInstance().addProdus(bauturaNou, this);
            addBautura(bauturaNou);
        }
        catch (SQLException e) {
            System.out.println("FAILED -> adaugareBauturaMenu()");
            e.printStackTrace();
        }
    }

    private void stergeBauturaMenu() {
        System.out.println("\nAlegeti produsul:");

        for (int i = 0; i < bauturi.size(); i++) {
            System.out.println(bauturi.get(i).getID() + " -> " + bauturi.get(i).getNume());
        }

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int option = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < bauturi.size(); i++) {
            if (bauturi.get(i).getID() == option) {
                try {
                    Database.getInstance().deleteProdus(option);
                    bauturi.remove(i);
                    System.out.println("Bautura a fost stersa din baza de date");
                    return;
                }
                catch (SQLException e) {
                    System.out.println("FAILED -> stergeBauturaMenu()");
                    e.printStackTrace();
                }
            }
        }

        System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
    }

    private void editareBautura() {
        System.out.println("\nAlegeti bautura:");

        for (int i = 0; i < bauturi.size(); i++) {
            System.out.println(bauturi.get(i).getID() + " -> " + bauturi.get(i).getNume());
        }

        System.out.print("Optiune: ");

        if (!scanner.hasNextInt()) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            scanner.next();
            return;
        }

        int optionBautura = scanner.nextInt();
        scanner.nextLine();

        Bautura bautura = null;
        for (int i = 0; i < bauturi.size(); i++) {
            if (bauturi.get(i).getID() == optionBautura) {
                bautura = bauturi.get(i);
            }
        }

        if (bautura == null) {
            System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            return;
        }

        System.out.println("\nAlegeti ce vreti sa editati:");
        System.out.println("1. Nume");
        System.out.println("2. Descriere");
        System.out.println("3. Pret");
        System.out.println("4. Ml");

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
                bautura.editNume();
                break;

            case 2:
                bautura.editDescriere();
                break;

            case 3:
                bautura.editPret();
                break;

            case 4:
                bautura.editMl();
                break;

            default:
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                break;
        }
    }

    @Override
    public void afisareDatePersonaleMenu() {
        super.afisareDatePersonaleMenu();
        System.out.println("Specializare: " + this.specializare);
    }

    public void editSpecializare() {
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
            Database.getInstance().editStringValue("barman", "id_angajat", "specializare", specializareNou, this.getID());
            this.specializare = specializareNou;
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editSpecializare()");
            e.printStackTrace();
        }
    }

    public ArrayList<Bautura> getBauturi() {
        return bauturi;
    }
}

