package App;

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
                restaurante.put(rs.getInt(1), new Restaurant(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getString(4), rs.getString(5), rs.getString(6)));
            }

            // angajati
            rs = connection.prepareStatement(SQLManager).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new Manager(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), null, restaurante.get(rs.getInt(3)), rs.getString(10)));
            }

            rs = connection.prepareStatement(SQLSefBucatar).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new SefBucatar(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), angajati.get(rs.getInt(2)), restaurante.get(rs.getInt(3)), rs.getString(10)));
                ((Manager) angajati.get(rs.getInt(2))).addSubordonat(angajati.get(rs.getInt(1)));
            }

            rs = connection.prepareStatement(SQLBarman).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new Barman(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), angajati.get(rs.getInt(2)), restaurante.get(rs.getInt(3)), rs.getString(10)));
                ((Manager) angajati.get(rs.getInt(2))).addSubordonat(angajati.get(rs.getInt(1)));
            }

            rs = connection.prepareStatement(SQLOspatar).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new Ospatar(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), angajati.get(rs.getInt(2)), restaurante.get(rs.getInt(3)), rs.getString(10)));
                ((Manager) angajati.get(rs.getInt(2))).addSubordonat(angajati.get(rs.getInt(1)));
            }

            // clienti
            // TODO
        }
        catch (SQLException e) {
            System.out.println("exceptie SQLException");
            e.printStackTrace();
        }
    }

    public void addAngajat(Angajat angajat) throws SQLException {
        angajati.put(angajat.getID(), angajat);

        String SQLInsertAngajat = "INSERT INTO ANGAJAT (id_angajat, id_manager, id_restaurant, username, password, nume, prenume, salariu, nr_telefon)" + "\n"
                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLInsertAngajat);
        preparedStatement.setInt(1, angajat.getID());
        preparedStatement.setInt(2, angajat.getManager().getID());
        preparedStatement.setInt(3, angajat.getRestaurant().getID());
        preparedStatement.setString(4, angajat.getUsername());
        preparedStatement.setString(5, angajat.getPassword());
        preparedStatement.setString(6, angajat.getNume());
        preparedStatement.setString(7, angajat.getPrenume());
        preparedStatement.setInt(8, angajat.getSalariu());
        preparedStatement.setString(9, angajat.getNrTelefon());

        preparedStatement.executeUpdate();

        if (angajat instanceof Manager) {
            String SQLInsertManager = "INSERT INTO manager (id_angajat, nivel_educatie)" + "\n"
                                    + "VALUES (?, ?);";

            preparedStatement = connection.prepareStatement(SQLInsertManager);
            preparedStatement.setInt(1, angajat.getID());
            preparedStatement.setString(2, ((Manager) angajat).getNivelEducatie());

            preparedStatement.executeUpdate();
        }
        else if (angajat instanceof Ospatar) {
            String SQLInsertOspatar = "INSERT INTO ospatar (id_angajat, nivel_engleza)" + "\n"
                                    + "VALUES (?, ?);";

            preparedStatement = connection.prepareStatement(SQLInsertOspatar);
            preparedStatement.setInt(1, angajat.getID());
            preparedStatement.setString(2, ((Ospatar) angajat).getNivelEngleza());

            preparedStatement.executeUpdate();
        }
        else if (angajat instanceof SefBucatar) {
            String SQLInsertSefBucatar = "INSERT INTO sef_bucatar (id_angajat, specializare)" + "\n"
                                       + "VALUES (?, ?);";

            preparedStatement = connection.prepareStatement(SQLInsertSefBucatar);
            preparedStatement.setInt(1, angajat.getID());
            preparedStatement.setString(2, ((SefBucatar) angajat).getSpecializare());

            preparedStatement.executeUpdate();
        }
        else if (angajat instanceof Barman) {
            String SQLInsertBarman = "INSERT INTO barman (id_angajat, specializare)" + "\n"
                                   + "VALUES (?, ?);";

            preparedStatement = connection.prepareStatement(SQLInsertBarman);
            preparedStatement.setInt(1, angajat.getID());
            preparedStatement.setString(2, ((Barman) angajat).getSpecializare());

            preparedStatement.executeUpdate();
        }
    }

    public void deleteAngajat(int ID) throws SQLException {
        angajati.remove(ID);

        String SQLDeleteAngajat = "DELETE FROM angajat" + "\n"
                                + "WHERE id_angajat = ?;";

        String SQLDeleteManager = "DELETE FROM manager" + "\n"
                                + "WHERE id_angajat = ?;";

        String SQLDeleteOspatar = "DELETE FROM ospatar" + "\n"
                                + "WHERE id_angajat = ?;";

        String SQLDeleteSefBucatar = "DELETE FROM sef_bucatar" + "\n"
                                + "WHERE id_angajat = ?;";

        String SQLDeleteBarman = "DELETE FROM barman" + "\n"
                                + "WHERE id_angajat = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLDeleteManager);
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(SQLDeleteOspatar);
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(SQLDeleteSefBucatar);
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(SQLDeleteBarman);
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(SQLDeleteAngajat);
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();
    }

    public void editAngajat(String attr, Integer value, Integer ID) throws SQLException {
        String SQLEditAngajat = "UPDATE angajat" + "\n"
                              + "SET " + attr + " = ?" + "\n"
                              + "WHERE id_angajat = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLEditAngajat);
        preparedStatement.setInt(1, value);
        preparedStatement.setInt(2, ID);
        preparedStatement.executeUpdate();
    }
}

