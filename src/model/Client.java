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
            System.out.println("3. Anleaza o comanda");
            System.out.println("4. Editare comanda");
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
                    afisareDatePersonaleMenu();
                    break;

                case 6:
                    // TODO
                    break;

                case 7:
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
        // TODO
    }

    public void afisareDatePersonaleMenu() {
        System.out.println();
        System.out.println("username: " + this.username);
        System.out.println("nume: " + this.nume);
        System.out.println("prenume: " + this.prenume);
        System.out.println("numar telefon: " + this.nrTelefon);
        System.out.println("email: " + this.email);
    }
}

