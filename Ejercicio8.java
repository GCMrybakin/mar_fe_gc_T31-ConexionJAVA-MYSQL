import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio8 {
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

            String createCajerosTableSQL = "CREATE TABLE CAJEROS (" +
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "NomApels NVARCHAR(255) NOT NULL)";
            statement.executeUpdate(createCajerosTableSQL);
            System.out.println("Tabla 'CAJEROS' creada con éxito.");

            String insertCajerosSQL = "INSERT INTO CAJEROS (NomApels) VALUES (?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertCajerosSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Cajero " + i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'CAJEROS'.");

            String createProductosTableSQL = "CREATE TABLE PRODUCTOS (" +
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Nombre NVARCHAR(100) NOT NULL, " +
                    "Precio INT NOT NULL)";
            statement.executeUpdate(createProductosTableSQL);
            System.out.println("Tabla 'PRODUCTOS' creada con éxito.");

            String insertProductosSQL = "INSERT INTO PRODUCTOS (Nombre, Precio) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertProductosSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Producto " + i);
                    preparedStatement.setInt(2, 100 * i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'PRODUCTOS'.");

            String createMaquinasRegistradasTableSQL = "CREATE TABLE MAQUINAS_REGISTRADAS (" +
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Piso INT NOT NULL)";
            statement.executeUpdate(createMaquinasRegistradasTableSQL);
            System.out.println("Tabla 'MAQUINAS_REGISTRADAS' creada con éxito.");

            String insertMaquinasRegistradasSQL = "INSERT INTO MAQUINAS_REGISTRADAS (Piso) VALUES (?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertMaquinasRegistradasSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'MAQUINAS_REGISTRADAS'.");

            String createVentaTableSQL = "CREATE TABLE VENTA (" +
                    "Cajero INT, " +
                    "Maquina INT, " +
                    "Producto INT, " +
                    "PRIMARY KEY (Cajero, Maquina, Producto), " +
                    "FOREIGN KEY (Cajero) REFERENCES CAJEROS(Codigo), " +
                    "FOREIGN KEY (Maquina) REFERENCES MAQUINAS_REGISTRADAS(Codigo), " +
                    "FOREIGN KEY (Producto) REFERENCES PRODUCTOS(Codigo))";
            statement.executeUpdate(createVentaTableSQL);
            System.out.println("Tabla 'VENTA' creada con éxito.");

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
