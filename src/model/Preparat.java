package model;

import App.Database;

import java.sql.SQLException;

public class Preparat extends Produs {
    Integer grame;

    public Preparat(Integer ID, String nume, String descriere, Integer pret, Integer grame) {
        super(ID, nume, descriere, pret);
        this.grame = grame;
    }

    public Integer getGrame() {
        return grame;
    }

    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Grame: " + grame;
    }

    public void editGrame() {
        Integer grameNou = null;
        while (true) {
            System.out.print("grame: ");

            if (!scanner.hasNextInt()) {
                System.out.println("cantitatea in grame trebuie sa fie un numar strict pozitiv!");
                scanner.next();
                continue;
            }

            grameNou = scanner.nextInt();
            scanner.nextLine();

            if (grameNou <= 0) {
                System.out.println("cantitatea in grame trebuie sa fie un numar strict pozitiv!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editIntValue("preparat", "id_produs", "grame", grameNou, this.getID());
            this.grame = grameNou;
            System.out.println("cantitatea in grame a fost modificata cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editGrame()");
            e.printStackTrace();
        }
    }
}

