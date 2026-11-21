package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBUtil {

    // Your MySQL config
    private static final String URL =
        "jdbc:mysql://127.0.0.1:3306/mydb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";              // from Workbench
    private static final String PASS = ""; // put your real root password here

    static {
        try {
            // MySQL Connector/J driver
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found. Make sure the connector JAR is on the classpath.");
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // Test method – you can keep or remove later
    public static void main(String[] args) {
        try (Connection conn = getConnection()) {
            System.out.println("Connection OK: " + !conn.isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Optional helper
    public static void closeQuietly(AutoCloseable ac) {
        if (ac != null) {
            try { ac.close(); } catch (Exception ignored) {}
        }
    }
}


