import model.Restaurant;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;

import model.*;

public class Database {
    private static Database instance = null;

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/LantRestaurante";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";

    private static Connection connection = null;

    private Map<Integer, Restaurant> restaurante;
    private Map<Integer, Angajat> angajati;

    // TODO: bloc normal? / bloc static? / constructor?
    static {
        loadDriver();
        createConnection();
    }

    private Database() {
        restaurante = new HashMap<>();
        angajati = new HashMap<>();

        loadDatabase();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
    }

    public Map<Integer, Restaurant> getRestaurante() {
        return restaurante;
    }

    public Map<Integer, Angajat> getAngajati() {
        return angajati;
    }

    private static void loadDriver() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (ClassNotFoundException e) {
            System.out.println("FAILED -> loadDriver()");
            e.printStackTrace();
        }
    }

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
        }
        catch (SQLException e) {
            System.out.println("FAILED -> createConnection()");
            e.printStackTrace();
        }
    }

    private void loadDatabase() {
        // SQL
        String SQLRestaurant = "SELECT *" + "\n" +
                               "FROM restaurant;";

        String SQLManager = "SELECT a.id_angajat, a.id_manager, a.id_restaurant, a.username, a.password, a.nume, a.prenume, a.salariu, a.nr_telefon, m.nivel_educatie" + "\n" +
                            "FROM angajat a JOIN manager m ON (a.id_angajat = m.id_angajat);";

        String SQLSefBucatar = "SELECT a.id_angajat, a.id_manager, a.id_restaurant, a.username, a.password, a.nume, a.prenume, a.salariu, a.nr_telefon, sb.specializare" + "\n" +
                               "FROM angajat a JOIN sef_bucatar sb ON (a.id_angajat = sb.id_angajat);";

        String SQLBarman = "SELECT a.id_angajat, a.id_manager, a.id_restaurant, a.username, a.password, a.nume, a.prenume, a.salariu, a.nr_telefon, b.specializare" + "\n" +
                           "FROM angajat a JOIN barman b ON (a.id_angajat = b.id_angajat);";

        String SQLOspatar = "SELECT a.id_angajat, a.id_manager, a.id_restaurant, a.username, a.password, a.nume, a.prenume, a.salariu, a.nr_telefon, o.nivel_engleza" + "\n" +
                            "FROM angajat a JOIN ospatar o ON (a.id_angajat = o.id_angajat);";

        // execute SQL
        try {
            // restaurante
            ResultSet rs = connection.prepareStatement(SQLRestaurant).executeQuery();
            while (rs.next()) {
                restaurante.put(rs.getInt(1), new Restaurant(rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }

            // angajati
            rs = connection.prepareStatement(SQLManager).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new Manager(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), null, restaurante.get(rs.getInt(3)), rs.getString(10)));
            }

            rs = connection.prepareStatement(SQLSefBucatar).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new SefBucatar(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), angajati.get(rs.getInt(2)), restaurante.get(rs.getInt(3)), rs.getString(10)));
            }

            rs = connection.prepareStatement(SQLBarman).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new Barman(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), angajati.get(rs.getInt(2)), restaurante.get(rs.getInt(3)), rs.getString(10)));
            }

            rs = connection.prepareStatement(SQLOspatar).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new Ospatar(rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), angajati.get(rs.getInt(2)), restaurante.get(rs.getInt(3)), rs.getString(10)));
            }

            // clienti
            // TODO
        }
        catch (SQLException e) {
            System.out.println("exceptie SQLException");
            e.printStackTrace();
        }
    }
}

