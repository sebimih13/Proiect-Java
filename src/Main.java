import App.AppService;
import App.Database;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // init App.Database
        Database.getInstance();

        // init App.AppService
        AppService.getInstance();

        // run App.AppService
        AppService.getInstance().run();
    }
}

// TODO: baza de date -> oricate caractere pt VARCHAR

/*

    - Comanda
        => la afisare adauga pretul final

*/

