import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio4 {
	public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/";
        String usuario = "root";
        String contraseña = "root9";
        String schema = "EjerciciosTA31";

        Connection conexion = null;
        try {
        	conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión exitosa a la base de datos.");
            Statement statement = conexion.createStatement();

            String createSchemaSQL = "CREATE SCHEMA IF NOT EXISTS " + schema;
            statement.executeUpdate(createSchemaSQL);
            System.out.println("Esquema '" + schema + "' creado con éxito.");

            String useSchemaSQL = "USE " + schema;
            statement.executeUpdate(useSchemaSQL);
            System.out.println("Usando el esquema '" + schema + "'.");

            String createSalasTableSQL = "CREATE TABLE SALAS (" +
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Nombre NVARCHAR(100), " +
                    "Pelicula INT)";
            statement.executeUpdate(createSalasTableSQL);

            String insertSalasSQL = "INSERT INTO SALAS (Nombre, Pelicula) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertSalasSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Sala " + i);
                    preparedStatement.setInt(2, i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Tabla 'SALAS' creada satisfactoriamente");

            String createPeliculasTableSQL = "CREATE TABLE PELICULAS (" +
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Nombre NVARCHAR(100), " +
                    "CalificacionEdad INT)";
            statement.executeUpdate(createPeliculasTableSQL);

            String insertPeliculasSQL = "INSERT INTO PELICULAS (Nombre, CalificacionEdad) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertPeliculasSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Pelicula " + i);
                    preparedStatement.setInt(2, 12 + i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Tabla 'PELICULAS' creada satisfactoriamente");

            System.out.println("Base de datos y tablas creadas, y registros insertados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}