import java.util.Scanner;

public class AppService {
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
            System.out.println("1. Logare");
            System.out.println("2. Iesire");

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
                    loginMenu();
                    break;

                case 2:
                    isRunning = false;
                    break;

                default:
                    System.out.println("Optiune invalida! Alegeti un numar din optiunile date!");
            }
        }
    }

    public void loginMenu() {
        while (isRunning) {
            System.out.println();

            System.out.print("username: ");
            String username = scanner.nextLine();

            System.out.print("password: ");
            String password = scanner.nextLine();

            System.out.println("username: " + username);
            System.out.println("password: " + password);

            // verificare daca exista in baza de date acest utilizator
            // TODO
        }
    }
}

