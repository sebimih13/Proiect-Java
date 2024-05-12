package App;

import java.sql.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Objects;

import model.*;

public class Database {
    private static Database instance = null;

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/LantRestaurante";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";

    private static Connection connection = null;

    private Map<Integer, Restaurant> restaurante;
    private Map<Integer, Angajat> angajati;
    private Map<Integer, Produs> produse;
    private Map<Integer, Comanda> comenzi;
    private Map<Integer, Client> clienti;

    static {
        loadDriver();
        createConnection();
    }

    private Database() {
        restaurante = new HashMap<>();
        angajati = new HashMap<>();
        produse = new HashMap<>();
        comenzi = new HashMap<>();
        clienti = new HashMap<>();

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

    public Map<Integer, Produs> getProduse() {
        return produse;
    }

    public Map<Integer, Comanda> getComenzi() {
        return comenzi;
    }

    public Map<Integer, Client> getClienti() {
        return clienti;
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

        String SQLPreparat = "SELECT p.id_produs, p.nume, p.descriere, p.pret, pr.grame, sb.id_angajat" + "\n"
                           + "FROM produs p JOIN preparat pr ON (p.id_produs = pr.id_produs)" + "\n"
                           + "              JOIN gateste g ON (p.id_produs = g.id_produs)" + "\n"
                           + "              JOIN sef_bucatar sb ON (g.id_angajat = sb.id_angajat);";

        String SQLBautura = "SELECT p.id_produs, p.nume, p.descriere, p.pret, b.ml, bar.id_angajat" + "\n"
                          + "FROM produs p JOIN bautura b ON (p.id_produs = b.id_produs)" + "\n"
                          + "              JOIN prepara pre ON (p.id_produs = pre.id_produs)" + "\n"
                          + "              JOIN barman bar ON (pre.id_angajat = bar.id_angajat);";

        String SQLComanda = "SELECT c.id_comanda, c.id_client, r.id_restaurant, c.status, c.data, c.ora" + "\n"
                          + "FROM comanda c JOIN restaurant r ON (c.id_restaurant = r.id_restaurant);";

        String SQLComandaContine = "SELECT c.id_comanda, p.id_produs, con.cantitate" + "\n"
                                 + "FROM comanda c JOIN contine con ON (c.id_comanda = con.id_comanda)" + "\n"
                                 + "               JOIN produs p ON (con.id_produs = p.id_produs);";

        String SQLClient = "SELECT c.id_client, c.username, c.password, c.nume, c.prenume, c.nr_telefon, c.email" + "\n"
                         + "FROM client c;";

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
                restaurante.get(rs.getInt(3)).addAngajat(angajati.get(rs.getInt(1)));
            }

            rs = connection.prepareStatement(SQLSefBucatar).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new SefBucatar(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), angajati.get(rs.getInt(2)), restaurante.get(rs.getInt(3)), rs.getString(10)));
                ((Manager) angajati.get(rs.getInt(2))).addSubordonat(angajati.get(rs.getInt(1)));
                restaurante.get(rs.getInt(3)).addAngajat(angajati.get(rs.getInt(1)));
            }

            rs = connection.prepareStatement(SQLBarman).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new Barman(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), angajati.get(rs.getInt(2)), restaurante.get(rs.getInt(3)), rs.getString(10)));
                ((Manager) angajati.get(rs.getInt(2))).addSubordonat(angajati.get(rs.getInt(1)));
                restaurante.get(rs.getInt(3)).addAngajat(angajati.get(rs.getInt(1)));
            }

            rs = connection.prepareStatement(SQLOspatar).executeQuery();
            while (rs.next()) {
                angajati.put(rs.getInt(1), new Ospatar(rs.getInt(1), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(8), rs.getString(9), angajati.get(rs.getInt(2)), restaurante.get(rs.getInt(3)), rs.getString(10)));
                ((Manager) angajati.get(rs.getInt(2))).addSubordonat(angajati.get(rs.getInt(1)));
                restaurante.get(rs.getInt(3)).addAngajat(angajati.get(rs.getInt(1)));
            }

            // produse
            rs = connection.prepareStatement(SQLPreparat).executeQuery();
            while (rs.next()) {
                produse.put(rs.getInt(1), new Preparat(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
                ((SefBucatar) angajati.get(rs.getInt(6))).addPreparat((Preparat) produse.get(rs.getInt(1))); // TODO: adauga o copie / clona
            }

            rs = connection.prepareStatement(SQLBautura).executeQuery();
            while (rs.next()) {
                produse.put(rs.getInt(1), new Bautura(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4), rs.getInt(5)));
                ((Barman) angajati.get(rs.getInt(6))).addBautura((Bautura) produse.get(rs.getInt(1))); // TODO: adauga o copie / clona
            }

            // clienti
            rs = connection.prepareStatement(SQLClient).executeQuery();
            while (rs.next()) {
                clienti.put(rs.getInt(1), new Client(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7)));
            }

            // comenzi
            rs = connection.prepareStatement(SQLComanda).executeQuery();
            while (rs.next()) {

                comenzi.put(rs.getInt(1), new Comanda(rs.getInt(1), (Objects.equals(rs.getString(4), "InPregatire")) ? Comanda.Status.InPregatire : Comanda.Status.Livrata, rs.getDate(5), rs.getTime(6), restaurante.get(rs.getInt(3))));
                restaurante.get(rs.getInt(3)).addComanda(comenzi.get(rs.getInt(1)));
                rs.getInt(2);
                if (!rs.wasNull()) {
                    clienti.get(rs.getInt(2)).addComanda(comenzi.get(rs.getInt(1)));
                }
            }

            // continut comanda
            rs = connection.prepareStatement(SQLComandaContine).executeQuery();
            while (rs.next()) {
                comenzi.get(rs.getInt(1)).adaugaProdus(produse.get(rs.getInt(2)), rs.getInt(3));
            }
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

        // TODO: DELETE FROM prepara + gateste
    }

    public void addProdus(Produs produs, Angajat angajat) throws SQLException {
        produse.put(produs.getID(), produs);

        String SQLInsertProdus = "INSERT INTO produs (id_produs, nume, descriere, pret)" + "\n"
                               + "VALUES (?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLInsertProdus);
        preparedStatement.setInt(1, produs.getID());
        preparedStatement.setString(2, produs.getNume());
        preparedStatement.setString(3, produs.getDescriere());
        preparedStatement.setInt(4, produs.getPret());

        preparedStatement.executeUpdate();

        if (produs instanceof Preparat) {
            String SQLInsertPreparat = "INSERT INTO preparat (id_produs, grame)" + "\n"
                                     + "VALUES (?, ?);";

            preparedStatement = connection.prepareStatement(SQLInsertPreparat);
            preparedStatement.setInt(1, produs.getID());
            preparedStatement.setInt(2, ((Preparat) produs).getGrame());

            preparedStatement.executeUpdate();

            String SQLInsertGateste = "INSERT INTO gateste (id_angajat, id_produs)" + "\n"
                                    + "VALUES (?, ?);";

            preparedStatement = connection.prepareStatement(SQLInsertGateste);
            preparedStatement.setInt(1, angajat.getID());
            preparedStatement.setInt(2, produs.getID());

            preparedStatement.executeUpdate();
        }
        else if (produs instanceof Bautura) {
            String SQLInsertBautura = "INSERT INTO bautura (id_produs, ml)" + "\n"
                                    + "VALUES (?, ?);";

            preparedStatement = connection.prepareStatement(SQLInsertBautura);
            preparedStatement.setInt(1, produs.getID());
            preparedStatement.setInt(2, ((Bautura) produs).getMl());

            preparedStatement.executeUpdate();

            String SQLInsertPrepara = "INSERT INTO prepara (id_angajat, id_produs)" + "\n"
                                    + "VALUES (?, ?);";

            preparedStatement = connection.prepareStatement(SQLInsertPrepara);
            preparedStatement.setInt(1, angajat.getID());
            preparedStatement.setInt(2, produs.getID());

            preparedStatement.executeUpdate();
        }
    }

    public void deleteProdus(int ID) throws SQLException {
        produse.remove(ID);

        String SQLDeleteProdus = "DELETE FROM produs" + "\n"
                               + "WHERE id_produs = ?;";

        String SQLDeletePreparat = "DELETE FROM preparat" + "\n"
                                 + "WHERE id_produs = ?;";

        String SQLDeleteBautura = "DELETE FROM bautura" + "\n"
                                + "WHERE id_produs = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLDeletePreparat);
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(SQLDeleteBautura);
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        preparedStatement = connection.prepareStatement(SQLDeleteProdus);
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();

        // TODO: DELETE FROM prepara + gateste
    }

    public void addComanda(Comanda comanda, Client client) throws SQLException {
        // comenzi.put(comanda.getID(), comanda);

        String SQLInsertComanda = "INSERT INTO comanda (id_comanda, id_client, id_restaurant, status, data, ora)" + "\n"
                                + "VALUES (?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLInsertComanda);
        preparedStatement.setInt(1, comanda.getID());
        if (client != null) {
            preparedStatement.setInt(2, client.getID());
        } else {
            preparedStatement.setNull(2, java.sql.Types.INTEGER);
        }
        preparedStatement.setInt(3, comanda.getRestaurant().getID());
        preparedStatement.setString(4, comanda.getStatus().toString());
        preparedStatement.setDate(5, comanda.getData());
        preparedStatement.setTime(6, comanda.getOra());

        preparedStatement.executeUpdate();

        for (Produs produs : comanda.getProduse()) {
            addContine(produs, comanda);
        }
    }

    public void deleteComanda(int ID) throws SQLException {
        String SQLDeleteComanda = "DELETE FROM comanda" + "\n"
                                + "WHERE id_comanda = ?;";

        for (Produs produs : comenzi.get(ID).getProduse()) {
            deleteContine(produs, comenzi.get(ID));
        }

        PreparedStatement preparedStatement = connection.prepareStatement(SQLDeleteComanda);
        preparedStatement.setInt(1, ID);
        preparedStatement.executeUpdate();
    }

    public void addContine(Produs produs, Comanda comanda) throws SQLException {
        String SQLInsertContine = "INSERT INTO contine (id_produs, id_comanda, cantitate)" + "\n"
                                + "VALUES (?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLInsertContine);
        preparedStatement.setInt(1, produs.getID());
        preparedStatement.setInt(2, comanda.getID());
        preparedStatement.setInt(3, comanda.getCantitati().get(produs));

        preparedStatement.executeUpdate();
    }

    public void deleteContine(Produs produs, Comanda comanda) throws SQLException {
        String SQLDeleteContine = "DELETE FROM contine" + "\n"
                                + "WHERE id_produs = ? AND id_comanda = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLDeleteContine);
        preparedStatement.setInt(1, produs.getID());
        preparedStatement.setInt(2, comanda.getID());
        preparedStatement.executeUpdate();
    }

    public void addClient(Client client) throws SQLException {
        clienti.put(client.getID(), client);

        String SQLInsertClient = "INSERT INTO client (id_client, username, password, nume, prenume, nr_telefon, email)" + "\n"
                               + "VALUES (?, ?, ?, ?, ?, ?, ?);";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLInsertClient);
        preparedStatement.setInt(1, client.getID());
        preparedStatement.setString(2, client.getUsername());
        preparedStatement.setString(3, client.getPassword());
        preparedStatement.setString(4, client.getNume());
        preparedStatement.setString(5, client.getPrenume());
        preparedStatement.setString(6, client.getNrTelefon());
        preparedStatement.setString(7, client.getEmail());

        preparedStatement.executeUpdate();
    }

    public void deleteClient(Integer ID) throws SQLException {
        clienti.remove(ID);

        String SQLDeleteClient = "DELETE FROM client" + "\n"
                               + "WHERE id_client = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLDeleteClient);
        preparedStatement.setInt(1, ID);

        preparedStatement.executeUpdate();
    }

    public void editIntValue(String tabel, String StringID, String attr, Integer value, Integer ID) throws SQLException {
        String SQLEdit = "UPDATE " + tabel + "\n"
                       + "SET " + attr + " = ?" + "\n"
                       + "WHERE " + StringID + " = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLEdit);
        preparedStatement.setInt(1, value);
        preparedStatement.setInt(2, ID);
        preparedStatement.executeUpdate();
    }

    public void editStringValue(String tabel, String StringID, String attr, String value, Integer ID) throws  SQLException {
        String SQLEdit = "UPDATE " + tabel + "\n"
                       + "SET " + attr + " = ?" + "\n"
                       + "WHERE " + StringID + " = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLEdit);
        preparedStatement.setString(1, value);
        preparedStatement.setInt(2, ID);
        preparedStatement.executeUpdate();
    }

    public void editContine(Integer cantitate, Integer IDProdus, Integer IDComanda) throws  SQLException {
        String SQLEdit = "UPDATE contine" + "\n"
                       + "SET cantitate = ?" + "\n"
                       + "WHERE id_produs = ? AND id_comanda = ?;";

        PreparedStatement preparedStatement = connection.prepareStatement(SQLEdit);
        preparedStatement.setInt(1, cantitate);
        preparedStatement.setInt(2, IDProdus);
        preparedStatement.setInt(3, IDComanda);
        preparedStatement.executeUpdate();
    }
}

