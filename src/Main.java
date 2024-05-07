import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // init Database
        Database.getInstance();

        // init AppService
        AppService.getInstance();

        // run AppService
        AppService.getInstance().run();

        try {
            Database.getInstance().getRestaurants();
        }
        catch (SQLException e) {
            // TODO
        }
    }
}

// TODO: baza de date -> oricate caractere pt VARCHAR
// TODO: oras + tara -> pentru restaurant

/*

    Aplicatie gestionarea unui lant de restaurante
    - logare din perspectiva unui user
        v1:
        => alegere restaurant
        => alegere tip angajat
        => alegere dupa id + nume + prenume + numar_telefon
        
        v2:
        => username
        => parola

    - iesire din sistem 

    - Admin
        => poata sa faca orice
        => adauga angajati

    - Manager - limitat doar la restaurantul lui
        => adauga angajati noi -> Sef-Bucatar, Barman, Ospatar
        => modifica manager
        => modifica restaurantul
        => modifica salariul
        => modifica telefonul
        => modifica numele
        => modifica prenumele
        => modifica 

    - Sef-Bucatar
        => ponteaza orele muncite

    - Barman
        => ponteaza orele muncite

    - Ospatar
        => ponteaza orele muncite
        => adauga comanda in sistem

    - Client
        => poate sa initieze comenzi in aplicatie
            => selecteaza restaurantul
            => selecteaza data_livrare
            => selecteaza produse: preparate + bautura
        
    - Comanda
        => un tip de date
*/

