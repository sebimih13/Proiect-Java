package model;

import App.Database;

import java.sql.SQLException;

public class Bautura extends Produs {
    private Integer ml;

    public Bautura(Integer ID, String nume, String descriere, Integer pret, Integer ml) {
        super(ID, nume, descriere, pret);
        this.ml = ml;
    }

    public Integer getMl() {
        return ml;
    }

    @Override
    public String toString() {
        return super.toString() + "\n"
             + "Ml: " + ml;
    }

    public void editMl() {
        Integer mlNou = null;
        while (true) {
            System.out.print("ml: ");

            if (!scanner.hasNextInt()) {
                System.out.println("cantitatea trebuie sa fie un numar strict pozitiv!");
                scanner.next();
                continue;
            }

            mlNou = scanner.nextInt();
            scanner.nextLine();

            if (mlNou <= 0) {
                System.out.println("cantitatea trebuie sa fie un numar strict pozitiv!");
                continue;
            }

            break;
        }

        try {
            Database.getInstance().editIntValue("bautura", "id_produs", "ml", mlNou, this.getID());
            this.ml = mlNou;
            System.out.println("cantitatea a fost modificata cu succes!");
        }
        catch (SQLException e) {
            System.out.println("FAILED -> editMl()");
            e.printStackTrace();
        }
    }
}

