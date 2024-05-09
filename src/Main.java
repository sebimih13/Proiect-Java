import model.Angajat;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");

        // init Database
        Database.getInstance();

        // init AppService
        AppService.getInstance();

        // run AppService
        AppService.getInstance().run();
    }
}

// TODO: baza de date -> oricate caractere pt VARCHAR

/*

    Aplicatie gestionarea unui lant de restaurante
    
    - logare din perspectiva unui angajat/client/admin
        => username
        => parola
    - iesire din sistem 

    - Admin
        => poata sa faca orice
        => adauga angajati

    - Manager - limitat doar la restaurantul lui
        => afisare subordonati
        => adauga angajati noi -> Sef-Bucatar, Barman, Ospatar
        => sterge angajat
        => modifica restaurantul
        => modifica date angajat
            => modifica telefonul
            => modifica numele
            => modifica prenumele
            => modifica

    - Sef-Bucatar
        => ponteaza orele muncite -> interfata comuna?

    - Barman
        => ponteaza orele muncite -> interfata comuna?

    - Ospatar
        => ponteaza orele muncite -> interfata comuna?
        => modifica statusul unei comenzi

    - Client
        => poate sa initieze comenzi in aplicatie
            => selecteaza restaurantul
            => selecteaza data_livrare
            => selecteaza produse: preparate + bautura
        => anuleaza comanda
        
    - Comanda
        => un tip de date

    - Produs
        - Preparat
        - Bautura

*/

