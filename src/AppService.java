import java.util.Scanner;

public class AppService {
    enum Utilizator {
        Angajat,
        Client
    }

    private static AppService instance = null;

    private boolean isRunning;
    private final Scanner scanner;

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

    public void mainMenu() {
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

    public void loginMenu(Utilizator utilizator) {
        System.out.println();

        while (true) {
            System.out.print("username: ");
            String username = scanner.nextLine();
            if (username.isEmpty()) {
                System.out.println("username trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("password: ");
            String password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("password trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        // verificare daca exista in baza de date acest utilizator
        switch (utilizator) {
            case Angajat:
                // TODO
                break;

            case Client:
                // TODO
                break;
        }
    }

    public void newClientMenu() {
        System.out.println("\nCompletati urmatoarele informatii");

        while (true) {
            System.out.print("username: ");
            String username = scanner.nextLine();
            if (username.isEmpty()) {
                System.out.println("username trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("password: ");
            String password = scanner.nextLine();
            if (password.isEmpty()) {
                System.out.println("password trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("nume: ");
            String nume = scanner.nextLine();
            if (nume.isEmpty()) {
                System.out.println("numele trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("prenume: ");
            String prenume = scanner.nextLine();
            if (prenume.isEmpty()) {
                System.out.println("prenumele trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("email: ");
            String email = scanner.nextLine();
            if (email.isEmpty()) {
                System.out.println("email trebuie sa contina cel putin un caracter!");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("numar telefon: ");
            String nrTelefon = scanner.nextLine();
            if (nrTelefon.length() != 10 || !nrTelefon.matches("[0-9]+")) {
                System.out.println("numarul de telefon trebuie sa contina fix 10 cifre!");
                continue;
            }
            break;
        }

        // TODO: adaugare si celelalte atribute
        // TODO: adaugare in baza de date
    }
}

