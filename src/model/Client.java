package model;

import App.Database;

import java.sql.SQLException;
import java.util.*;

public class Client {
    public static Integer maxIDClient;

    private Integer ID;
    private String username;
    private String password;
    private String nume;
    private String prenume;
    private String nrTelefon;
    private String email;

    private List<Comanda> comenzi;

    protected final Scanner scanner;

    static {
        maxIDClient = 0;
    }

    public Client(Integer ID, String username, String password, String nume, String prenume, String nrTelefon, String email) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nume = nume;
        this.prenume = prenume;
        this.nrTelefon = nrTelefon;
        this.email = email;
        this.comenzi = new ArrayList<>();

        maxIDClient = Integer.max(maxIDClient, ID);
        this.scanner = new Scanner(System.in);
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

    public String getNrTelefon() {
        return nrTelefon;
    }

    public String getEmail() {
        return email;
    }

    public void addComanda(Comanda comanda) {
        comenzi.add(comanda);
    }

    public void menu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\nAlegeti o optiune:");
            System.out.println("1. Afisare comenzi in pregatire");
            System.out.println("2. Afisare comenzi livrate");
            System.out.println("3. Efectueaza o noua comanda");
            System.out.println("4. Anuleaza o comanda");
            System.out.println("5. Editare comanda");
            System.out.println("6. Afisare date personale");
            System.out.println("7. Editare date personale");
            System.out.println("8. Log out");

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
                    afisareComenziInPregatireMenu();
                    break;

                case 2:
                    afisareComenziLivrateMenu();
                    break;

                case 3:
                    adaugareComandaMenu();
                    break;

                case 4:
                    stergeComandaMenu();
                    break;

                case 5:
                    editareComandaMenu();
                    break;

                case 6:
                    afisareDatePersonaleMenu();
                    break;

                case 7:
                    editareDatePersonaleMenu();
                    break;

                case 8:
                    logout = true;
                    break;
            }
        }
    }

    private void afisareComenziInPregatireMenu() {
        for (Comanda comanda : comenzi) {
            if (comanda.getStatus() == Comanda.Status.InPregatire) {
                System.out.println(comanda);
            }
        }
    }

    private void afisareComenziLivrateMenu() {
        for (Comanda comanda : comenzi) {
            if (comanda.getStatus() == Comanda.Status.Livrata) {
                System.out.println(comanda);
            }
        }
    }

    private void adaugareComandaMenu() {
        System.out.println("Selecteaza restaurantul:");
        for (Map.Entry<Integer, Restaurant> entry : Database.getInstance().getRestaurante().entrySet()) {
            Restaurant restaurant = entry.getValue();
            System.out.println(restaurant);
        }

        Restaurant restaurant = null;
        while (restaurant == null) {
            System.out.print("Optiune: ");

            if (!scanner.hasNextInt()) {
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                scanner.next();
                continue;
            }

            int optionRestaurant = scanner.nextInt();
            scanner.nextLine();

            restaurant = Database.getInstance().getRestaurante().get(optionRestaurant);
        }

        Comanda comandaNoua = new Comanda(++Comanda.maxIDComanda, restaurant);

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

            comandaNoua.adaugaProdus(produse.get(option - 1), cantitate);
        }

        try {
            restaurant.addComanda(comandaNoua);
            Database.getInstance().addComanda(comandaNoua, this);
        }
        catch (SQLException e) {
            System.out.println("FAILED -> adaugareComandaMenu()");
            e.printStackTrace();
        }
    }

    private void stergeComandaMenu() {
        if (comenzi.size() == 0) {
            System.out.println("Nu exista comenzi in pregatire!");
            return;
        }

        for (int i = 0; i < comenzi.size(); i++) {
            if (comenzi.get(i).getStatus() == Comanda.Status.InPregatire) {
                System.out.println((i + 1) + ". " + comenzi.get(i));
            }
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
            comenzi.get(option - 1).getRestaurant().deleteComanda(comenzi.get(option - 1));
            comenzi.remove(comenzi.get(option - 1));
        }
        catch (SQLException e) {
            System.out.println("FAILED -> stergeComandaMenu()");
            e.printStackTrace();
        }
    }

    public void editareComandaMenu() {
        if (comenzi.size() == 0) {
            System.out.println("Nu exista comenzi in pregatire!");
            return;
        }

        for (int i = 0; i < comenzi.size(); i++) {
            if (comenzi.get(i).getStatus() == Comanda.Status.InPregatire) {
                System.out.println((i + 1) + ". " + comenzi.get(i));
            }
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
                editEmail();
                break;

            default:
                System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
                break;
        }
    }

    public void afisareDatePersonaleMenu() {
        System.out.println();
        System.out.println("username: " + this.username);
        System.out.println("nume: " + this.nume);
        System.out.println("prenume: " + this.prenume);
        System.out.println("numar telefon: " + this.nrTelefon);
        System.out.println("email: " + this.email);
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
            Database.getInstance().editStringValue("client", "id_client", "username", usernameNou, this.ID);
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
            Database.getInstance().editStringValue("client", "id_client", "password", passwordNou, this.ID);
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
            Database.getInstance().editStringValue("client", "id_client", "nume", numeNou, this.ID);
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
            Database.getInstance().editStringValue("client", "id_client", "prenume", prenumeNou, this.ID);
            this.prenume = prenumeNou;
            System.out.println("prenumele a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editPrenume()");
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
            Database.getInstance().editStringValue("client", "id_client", "nr_telefon", nrTelefonNou, this.ID);
            this.nrTelefon = nrTelefonNou;
            System.out.println("numarul de telefon a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editNrTelefon()");
            e.printStackTrace();
        }
    }

    public void editEmail() {
        String emailNou = null;
        while (true) {
            System.out.print("email: ");

            emailNou = scanner.nextLine();
            if (emailNou.isEmpty()) {
                System.out.println("email-ul trebuie sa contina cel putin un caracter!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editStringValue("client", "id_client", "email", emailNou, this.ID);
            this.email = emailNou;
            System.out.println("email-ul a fost modificat cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editEmail()");
            e.printStackTrace();
        }
    }
}

