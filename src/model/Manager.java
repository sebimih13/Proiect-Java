package model;

import java.util.Scanner;
import java.util.ArrayList;

public class Manager extends Angajat {
    private String nivelEducatie;
    private ArrayList<Angajat> subordonati;

    private final Scanner scanner;

    public Manager(String username, String password, String nume, String prenume, int salariu, String nrTelefon, Angajat manager, Restaurant restaurant, String nivelEducatie) {
        super(username, password, nume, prenume, salariu, nrTelefon, manager, restaurant);
        this.nivelEducatie = nivelEducatie;
        subordonati = new ArrayList<>();

        scanner = new Scanner(System.in);
    }

    public String getNivelEducatie() {
        return nivelEducatie;
    }

    @Override
    public final void menu() {
        boolean logout = false;
        while (!logout) {
            System.out.println("\nAlegeti o optiune:");
            System.out.println("1. Afisare subordonati");
            System.out.println("2. Adaugare angajat nou in restaurant");
            System.out.println("3. Sterge angajat din restaurant");
            System.out.println("4. Modifica datele unui angajat");
            System.out.println("5. Log out");

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
                    System.out.println("\nSubordonati:");
                    for (int i = 0; i < subordonati.size(); i++) {
                        System.out.println(subordonati.get(i));
                    }
                    break;

                case 2:
                    // TODO
                    break;

                case 3:
                    // TODO
                    break;

                case 4:
                    // TODO
                    break;

                case 5:
                    logout = true;
                    break;
            }
        }
    }

    public void addSubordonat(Angajat subordonat) {
        subordonati.add(subordonat);
    }
}

