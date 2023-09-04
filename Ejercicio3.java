import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio3 {
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

            String createAlmacenesTableSQL = "CREATE TABLE ALMACENES (" +
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "lugar NVARCHAR(100) NOT NULL, " +
                    "Capacidad INT NOT NULL)";
            statement.executeUpdate(createAlmacenesTableSQL);
            System.out.println("Tabla 'ALMACENES' creada con éxito.");

            String insertAlmacenesSQL = "INSERT INTO ALMACENES (lugar, Capacidad) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertAlmacenesSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Lugar " + i);
                    preparedStatement.setInt(2, 1000 * i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'ALMACENES'.");

            String createCajasTableSQL = "CREATE TABLE CAJAS (" +
                    "NumReferencia CHAR(5) PRIMARY KEY, " +
                    "Contenido NVARCHAR(100) NOT NULL, " +
                    "Valor INT NOT NULL, " +
                    "Almacen INT NOT NULL, " +
                    "FOREIGN KEY (Almacen) REFERENCES ALMACENES(Codigo))";
            statement.executeUpdate(createCajasTableSQL);
            System.out.println("Tabla 'CAJAS' creada con éxito.");

            String insertCajasSQL = "INSERT INTO CAJAS (NumReferencia, Contenido, Valor, Almacen) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertCajasSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Caja" + i);
                    preparedStatement.setString(2, "Contenido de la Caja " + i);
                    preparedStatement.setInt(3, 50 * i);
                    preparedStatement.setInt(4, i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'CAJAS'.");

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