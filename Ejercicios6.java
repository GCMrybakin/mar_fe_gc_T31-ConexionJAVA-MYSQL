import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicios6 {
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

            String createPiezasTableSQL = "CREATE TABLE PIEZAS (" +
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Nombre VARCHAR(100) NOT NULL)";
            statement.executeUpdate(createPiezasTableSQL);
            System.out.println("Tabla 'PIEZAS' creada");

            String insertPiezasSQL = "INSERT INTO PIEZAS (Nombre) VALUES (?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertPiezasSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Pieza " + i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Datos insertados a la tabla 'Piezas'");

            String createProveedoresTableSQL = "CREATE TABLE PROVEEDORES (" +
                    "Id CHAR(4) PRIMARY KEY, " +
                    "Nombre NVARCHAR(100) NOT NULL)";
            statement.executeUpdate(createProveedoresTableSQL);
            System.out.println("Tabla 'PROVEEDORES' creada");

            String insertProveedoresSQL = "INSERT INTO PROVEEDORES (Id, Nombre) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertProveedoresSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "P" + i);
                    preparedStatement.setString(2, "Proveedor " + i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Datos insertados a la tabla 'PROVEEDORES'");

            String createSuministraTableSQL = "CREATE TABLE SUMINISTRA (" +
                    "CodigoPieza INT, " +
                    "IdProveedor CHAR(4), " +
                    "Precio INT, " +
                    "PRIMARY KEY (CodigoPieza, IdProveedor), " +
                    "FOREIGN KEY (CodigoPieza) REFERENCES PIEZAS(Codigo), " +
                    "FOREIGN KEY (IdProveedor) REFERENCES PROVEEDORES(Id))";
            statement.executeUpdate(createSuministraTableSQL);
            System.out.println("Tabla 'SUMINISTRA' creada");

            String insertSuministraSQL = "INSERT INTO SUMINISTRA (CodigoPieza, IdProveedor, Precio) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertSuministraSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setInt(1, i);
                    preparedStatement.setString(2, "P" + i);
                    preparedStatement.setInt(3, 50 * i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Datos insertados a la tabla 'SUMINISTRA'");

            System.out.println("Base de datos creada y registros insertados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
