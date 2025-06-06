import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {
    private static final String URL = "jdbc:postgresql://localhost:5432/erp";
    private static final String USER = "postgres";
    private static final String PASSWORD = "root";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            System.out.println("Driver PostgreSQL chargé");

            // Optionnel : test de connexion à la base postgres (par défaut)
            try (Connection testConn = DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/postgres", USER, PASSWORD)) {
                System.out.println("Connexion à la base postgres réussie");
            }

            // Connexion à la base erp
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connexion à la base erp réussie");

            // Création de la table customers si elle n'existe pas
            try (Statement stmt = conn.createStatement()) {
                String sql = """
                    CREATE TABLE IF NOT EXISTS customers (
                        id SERIAL PRIMARY KEY,
                        firstname VARCHAR(100) NOT NULL,
                        lastname VARCHAR(100) NOT NULL,
                        phone VARCHAR(20),
                        email VARCHAR(100)
                    )
                    """;
                stmt.executeUpdate(sql);
                System.out.println("Table customers vérifiée/créée avec succès");

                // Vérifier que la table a bien été créée
                try (ResultSet rs = stmt.executeQuery(
                        "SELECT EXISTS (SELECT FROM information_schema.tables WHERE table_name = 'customers')")) {
                    if (rs.next() && rs.getBoolean(1)) {
                        System.out.println("Table customers existe bien");
                    } else {
                        throw new SQLException("La table customers n'a pas été créée correctement");
                    }
                }
            }

            // Attention : la connexion doit être fermée par l'appelant quand il a fini
            return conn;

        } catch (ClassNotFoundException e) {
            System.err.println("Driver PostgreSQL non trouvé: " + e.getMessage());
            e.printStackTrace();
            throw new SQLException("PostgreSQL JDBC Driver non trouvé.", e);
        } catch (SQLException e) {
            System.err.println("Erreur de connexion: " + e.getMessage());
            System.err.println("URL: " + URL);
            System.err.println("Utilisateur: " + USER);
            e.printStackTrace();
            throw e;
        }
    }
}
