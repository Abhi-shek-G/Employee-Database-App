import java.sql.*;

public class App {

    // Database credentials
    static final String DB_URL = "jdbc:mysql://localhost:3306/testdb?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String USER = "root";             // change to your username
    static final String PASS = "charlesDarwin@69";    // change to your password

    public static void main(String[] args) {
        try {
            // Load JDBC driver (important)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to database
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to MySQL");

            // Add user
            addUser(conn, "Alice", "alice@example.com");
            addUser(conn, "Bob", "bob@example.com");

            // View users
            viewUsers(conn);

            // Update user with id=1
            updateUser(conn, 1, "Alice Updated", "alice_new@example.com");

            // View again to confirm update
            viewUsers(conn);

            // Delete user with id=2
            deleteUser(conn, 2);

            // View again to confirm delete
            viewUsers(conn);

            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Add user to 'users' table
    public static void addUser(Connection conn, String name, String email) throws SQLException {
        String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            int rows = pstmt.executeUpdate();
            System.out.println("Inserted " + rows + " row(s).");
        }
    }

    // View all users
    public static void viewUsers(Connection conn) throws SQLException {
        String sql = "SELECT * FROM users";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Current users:");
            while (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                System.out.println(id + ": " + name + " - " + email);
            }
        }
    }

    // Update user by id
    public static void updateUser(Connection conn, int id, String name, String email) throws SQLException {
        String sql = "UPDATE users SET name = ?, email = ? WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, email);
            pstmt.setInt(3, id);
            int rows = pstmt.executeUpdate();
            System.out.println("Updated " + rows + " row(s).");
        }
    }

    // Delete user by id
    public static void deleteUser(Connection conn, int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            System.out.println("Deleted " + rows + " row(s).");
        }
    }
}
