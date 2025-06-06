import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    private static DatabaseManager instance;
    private Connection connection;

    private static final String URL = "jdbc:postgresql://localhost:5432/store"; // adapte ici
    private static final String USER = "postgres"; 
    private static final String PASSWORD = "root"; 

    private DatabaseManager() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver PostgreSQL chargé");
        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL non trouvé");
            e.printStackTrace();
            throw new SQLException("Driver PostgreSQL non trouvé", e);
        }
        connection = DriverManager.getConnection(URL, USER, PASSWORD);
        System.out.println("Connexion à la base de données établie");
    }

    public static synchronized DatabaseManager getInstance() throws SQLException {
        if (instance == null || instance.getConnection().isClosed()) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
