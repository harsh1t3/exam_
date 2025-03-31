import java.sql.*;

class dbConnector {
    // Use the updated JDBC driver class for MySQL 8.0+
    static final String jdbc_driver = "com.mysql.cj.jdbc.Driver"; 
    static final String db_url = "jdbc:mysql://localhost:3306/examsystem?serverTimezone=UTC";
    
    // MySQL credentials
    static String user = "user";
    static String pass = "1316";
    
    public static void main(String[] args) {
        // Use try-with-resources to manage database resources
        try (Connection conn = DriverManager.getConnection(db_url, user, pass);
             Statement stmt = conn.createStatement()) {

            // Uncomment and use for debugging
            // System.out.println("Database Selected");
            
            // Sample query to retrieve student results
            String qry = "SELECT S.sid, S.first_name, S.last_name, R.Total_Marks "
                    + "FROM Student S, ExamStudentTable E, Result R "
                    + "WHERE E.eid = 11 AND S.sid = E.sid AND R.estid = E.estid "
                    + "ORDER BY S.sid";

            // Execute the query
            try (ResultSet rs = stmt.executeQuery(qry)) {
                while (rs.next()) {
                    String name = rs.getString(2); // Get the first name of the student
                    System.out.println("Name: " + name);
                }
            }

        } catch (SQLException e) {
            // SQLException handling (e.g., connection issues, query issues)
            System.err.println("SQLException: " + e.getMessage());
        } catch (Exception e) {
            // General exception handling
            System.err.println("Exception: " + e.getMessage());
        }
    }

    // Method to execute any query (generic, returns ResultSet)
    public ResultSet executeQuery(String qry) {
        ResultSet rs = null;
        try (Statement stmt = dbConnector.conn.createStatement()) {
            rs = stmt.executeQuery(qry);
        } catch (SQLException exp) {
            System.err.println("SQLException in query: " + qry);
            exp.printStackTrace();
        } catch (Exception exp) {
            System.err.println("Exception in query: " + qry);
            exp.printStackTrace();
        }
        return rs;
    }
}
