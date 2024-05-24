package App;

import model.*;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Map;

public class AppService implements InputDatePersonale {
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
                    try {
                        newClientMenu();
                    }
                    catch (UniqueValueException e) {
                        System.out.println(e.getMessage());
                    }
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
                    AuditService.getInstance().writeAction("System", "System", "Autentificare angajat: " + username);
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
                    AuditService.getInstance().writeAction("System", "System", "Autentificare client: " + username);
                    clientAutentificat.menu();
                }
                break;
        }
    }

    private void newClientMenu() throws UniqueValueException {
        System.out.println("\nCompletati urmatoarele informatii:");

        String username = inputUsername();
        for (Map.Entry<Integer, Client> entry : Database.getInstance().getClienti().entrySet()) {
            Client client = entry.getValue();
            if (Objects.equals(client.getUsername(), username)) {
                throw new UniqueValueException("Exista deja in baza de date un client cu acest username");
            }
        }

        String password = inputPassword();

        String nume = inputNume();

        String prenume = inputPrenume();

        String email = inputEmail();
        for (Map.Entry<Integer, Client> entry : Database.getInstance().getClienti().entrySet()) {
            Client client = entry.getValue();
            if (Objects.equals(client.getEmail(), email)) {
                throw new UniqueValueException("Exista deja in baza de date un client cu acest email");
            }
        }

        String nrTelefon = inputNrTelefon();
        for (Map.Entry<Integer, Client> entry : Database.getInstance().getClienti().entrySet()) {
            Client client = entry.getValue();
            if (Objects.equals(client.getNrTelefon(), nrTelefon)) {
                throw new UniqueValueException("Exista deja in baza de date un client cu acest numar de telefon");
            }
        }

        try {
            Database.getInstance().addClient(new Client(++Client.maxIDClient, username, password, nume, prenume, nrTelefon, email));

            AuditService.getInstance().writeAction("System", "System", "Client nou: " + username);
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

