import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio1 {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String usuario = "root";
        String contraseña = "root9";
        String schema = "EjerciciosTA31";

        Connection conexion = null;
        try {
            conexion = DriverManager.getConnection(url, usuario, contraseña); // conectarse al server
            System.out.println("Conexión exitosa a la base de datos.");
            Statement statement = conexion.createStatement();

            String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS " + schema; // Crear el databasename a.k.a schema
            statement.executeUpdate(createSchemaSQL);
            System.out.println("Esquema '" + schema + "' creado con éxito.");

            String useSchemaSQL = "USE " + schema;
            statement.executeUpdate(useSchemaSQL);
            System.out.println("Usando el esquema '" + schema + "'.");

            String createFabricantesTableSQL = "CREATE TABLE FABRICANTES (" + // Crear la tabla Fabricantes
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Nombre NVARCHAR(100) NOT NULL)";
            statement.executeUpdate(createFabricantesTableSQL);
            System.out.println("Tabla 'FABRICANTES' creada con éxito.");

            String insertFabricantesSQL = "INSERT INTO FABRICANTES (Nombre) VALUES (?)"; // Inserts a la tabla Fabricantes
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertFabricantesSQL)) {
                preparedStatement.setString(1, "Fabricante A");
                preparedStatement.executeUpdate();
                preparedStatement.setString(1, "Fabricante B");
                preparedStatement.executeUpdate();
                preparedStatement.setString(1, "Fabricante C");
                preparedStatement.executeUpdate();
                preparedStatement.setString(1, "Fabricante D");
                preparedStatement.executeUpdate();
                preparedStatement.setString(1, "Fabricante E");
                preparedStatement.executeUpdate();
            }
            System.out.println("Registros insertados en la tabla 'FABRICANTES'.");

            String createArticulosTableSQL = "CREATE TABLE ARTICULOS (" + // Crear la tabla Articulos
                    "Identity INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Nombre NVARCHAR(100) NOT NULL, " +
                    "Precio INT NOT NULL, " +
                    "Fabricante INT NOT NULL, " +
                    "FOREIGN KEY (Fabricante) REFERENCES FABRICANTES(Codigo))";
            statement.executeUpdate(createArticulosTableSQL);
            System.out.println("Tabla 'ARTICULOS' creada con éxito.");

            String insertArticulosSQL = "INSERT INTO ARTICULOS (Nombre, Precio, Fabricante) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertArticulosSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Articulo " + i);
                    preparedStatement.setInt(2, 100 * i);
                    preparedStatement.setInt(3, i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'ARTICULOS'.");

            System.out.println("Base de datos creada y registros insertados correctamente.");
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        } finally {
            try {
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }
}
