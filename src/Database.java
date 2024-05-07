import model.Restaurant;

import java.sql.*;
import java.util.List;

public class Database {
    private static Database instance = null;

    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/LantRestaurante";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "root";

    private static Connection connection = null;

    // TODO: bloc normal? / bloc static? / constructor?
    static {
        loadDriver();
        createConnection();
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }

        return instance;
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

    public void getRestaurants() throws SQLException {
        ResultSet rs = connection.prepareStatement("SELECT * FROM angajat").executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1));
        }
    }
}

