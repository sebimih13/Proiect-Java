package App;

import model.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Map;

public class AppService {
    enum Utilizator {
        Angajat,
        Client
    }

    private static AppService instance = null;

    private boolean isRunning;
    private final Scanner scanner;

    private Angajat angajatAutentificat;
    private Client clientAutentificat;

    private AppService() {
        isRunning = true;

        scanner = new Scanner(System.in);
    }

    public static AppService getInstance() {
        if (instance == null) {
            instance = new AppService();
        }

        return instance;
    }

    public void run() {
        System.out.println("\tAplicatie pentru gestiunea unui lant de restaurante\n");

        mainMenu();
    }

    private void mainMenu() {
        while (isRunning) {
            System.out.println("\nAlegeti o optiune:");
            System.out.println("1. Logare angajat");
            System.out.println("2. Logare client");
            System.out.println("3. Creare cont de client");
            System.out.println("4. Iesire");

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
                    loginMenu(Utilizator.Angajat);
                    break;

                case 2:
                    loginMenu(Utilizator.Client);
                    break;

                case 3:
                    newClientMenu();
                    break;

                case 4:
                    isRunning = false;
                    break;

                default:
                    System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            }
        }
    }

    private void loginMenu(Utilizator utilizator) {
        System.out.println();

        String username = null;
        while (true) {
            System.out.print("username: ");
            username = scanner.nextLine();
            if (username.isEmpty()) {
                System.out.println("username trebuie sa contina cel putin un caracter!");
                continue;
            }
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

        // verificare daca exista in baza de date acest utilizator
        switch (utilizator) {
            case Angajat:
                angajatAutentificat = getAngajat(username, password);
                if (angajatAutentificat == null) {
                    System.out.println("Datele introduse sunt gresite");
                }
                else {
                    System.out.println("\nBine ai revenit!");
                    angajatAutentificat.menu();
                }
                break;

            case Client:
                clientAutentificat = getClient(username, password);
                if (clientAutentificat == null) {
                    System.out.println("Datele introduse sunt gresite");
                }
                else {
                    System.out.println("\nBine ai revenit!");
                    clientAutentificat.menu();
                }
                break;
        }
    }

    private void newClientMenu() {
        System.out.println("\nCompletati urmatoarele informatii:");

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

        String email = null;
        while (true) {
            System.out.print("email: ");
            email = scanner.nextLine();
            if (email.isEmpty()) {
                System.out.println("email trebuie sa contina cel putin un caracter!");
                continue;
            }
            // TODO: verificare sa nu existe deja in baza de date
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

        try {
            Database.getInstance().addClient(new Client(++Client.maxIDClient, username, password, nume, prenume, nrTelefon, email));
        }
        catch (SQLException e) {
            System.out.println("FAILED -> newClientMenu()");
            e.printStackTrace();
        }
    }

    private Angajat getAngajat(String username, String password) {
        for (Map.Entry<Integer, Angajat> entry : Database.getInstance().getAngajati().entrySet()) {
            Angajat angajat = entry.getValue();
            if (Objects.equals(angajat.getUsername(), username) && Objects.equals(angajat.getPassword(), password)) {
                return angajat;
            }
        }

        return null;
    }

    private Client getClient(String username, String password) {
        for (Map.Entry<Integer, Client> entry : Database.getInstance().getClienti().entrySet()) {
            Client client = entry.getValue();
            if (Objects.equals(client.getUsername(), username) && Objects.equals(client.getPassword(), password)) {
                return client;
            }
        }

        return null;
    }
}

