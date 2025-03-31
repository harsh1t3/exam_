package Login;

import java.sql.*;

public class dbConnector {
    // Updated JDBC driver for MySQL 8.0 and later
    private static final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";

    // Database URL with proper timezone and SSL settings
    private static final String DB_URL = "jdbc:mysql://localhost:3306/examsystem?serverTimezone=UTC&useSSL=false&allowPublicKeyRetrieval=true";

    // Credentials for MySQL
    private static final String USER = "user"; // Replace with your MySQL username
    private static final String PASS = "1316"; // Replace with your MySQL password

    // Connection object
    private Connection conn = null;

    // Constructor to establish connection to the database
    public dbConnector() {
        try {
            // Load MySQL JDBC driver
            Class.forName(JDBC_DRIVER);
            // Establish connection
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Database connected successfully.");
        } catch (ClassNotFoundException e) {
            // Handle missing JDBC driver
            System.err.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            // Handle SQL exceptions
            System.err.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            // Handle general exceptions
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Method to get the connection object
    public Connection getConnection() {
        if (conn == null) {
            System.err.println("Connection is not initialized. Please check the database connection.");
        }
        return conn;
    }

    // Method to execute SELECT queries and return a ResultSet
    public ResultSet executeQuery(String qry) {
        ResultSet rs = null;
        try (Statement stmt = conn.createStatement()) {
            rs = stmt.executeQuery(qry);
        } catch (SQLException e) {
            // SQL Error handling
            System.err.println("SQLException in query: " + qry);
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            // General Exception handling
            System.err.println("Exception: " + e.getMessage());
        }
        return rs;
    }

    // Method to execute INSERT, UPDATE, DELETE queries and return the number of affected rows
    public int executeUpdate(String qry) {
        int rowsAffected = 0;
        try (Statement stmt = conn.createStatement()) {
            rowsAffected = stmt.executeUpdate(qry);
        } catch (SQLException e) {
            // SQL Error handling
            System.err.println("SQLException in query: " + qry);
            System.err.println("Error: " + e.getMessage());
        } catch (Exception e) {
            // General Exception handling
            System.err.println("Exception: " + e.getMessage());
        }
        return rowsAffected;
    }

    // Method to execute parameterized queries (PreparedStatement)
    public PreparedStatement prepareStatement(String query) {
        PreparedStatement pstmt = null;
        try {
            pstmt = conn.prepareStatement(query);
        } catch (SQLException e) {
            System.err.println("SQLException in preparing statement: " + query);
            System.err.println("Error: " + e.getMessage());
        }
        return pstmt;
    }

    // Method to close the database connection
    public void closeConnection() {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            // Handle exception if closing connection fails
            System.err.println("SQLException when closing connection: " + e.getMessage());
        }
    }
}