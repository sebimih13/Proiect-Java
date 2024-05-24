package App;

import java.util.Scanner;

public interface InputDatePersonale {
    default String inputUsername() {
        Scanner scanner = new Scanner(System.in);

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

        return usernameNou;
    }

    default String inputPassword() {
        Scanner scanner = new Scanner(System.in);

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

        return passwordNou;
    }

    default String inputNume() {
        Scanner scanner = new Scanner(System.in);

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

        return numeNou;
    }

    default String inputPrenume() {
        Scanner scanner = new Scanner(System.in);

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

        return prenumeNou;
    }

    default String inputNrTelefon() {
        Scanner scanner = new Scanner(System.in);

        String nrTelefonNou = null;
        while (true) {
            System.out.print("numar telefon: ");

            nrTelefonNou = scanner.nextLine();
            if (nrTelefonNou.length() != 10 || !nrTelefonNou.matches("[0-9]+")) {
                System.out.println("numarul de telefon trebuie sa contina fix 10 cifre!");
                continue;
            }

            break;
        }

        return nrTelefonNou;
    }

    default String inputEmail() {
        Scanner scanner = new Scanner(System.in);

        String emailNou = null;
        while (true) {
            System.out.print("email: ");

            emailNou = scanner.nextLine();
            if (emailNou.isEmpty()) {
                System.out.println("email-ul trebuie sa contina cel putin un caracter!");
                continue;
            }

            if (!emailNou.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
                System.out.printf("adresa de email invalida!");
                continue;
            }

            break;
        }

        return emailNou;
    }
}

