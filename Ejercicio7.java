import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio7 {
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

            String createCientificosTableSQL = "CREATE TABLE CIENTIFICOS (" +
                    "DNI VARCHAR(8) PRIMARY KEY, " +
                    "NomApels NVARCHAR(255) NOT NULL)";
            statement.executeUpdate(createCientificosTableSQL);
            System.out.println("Tabla 'CIENTIFICOS' creada");
            
            String insertCientificosSQL = "INSERT INTO CIENTIFICOS (DNI, NomApels) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertCientificosSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "DNI" + i);
                    preparedStatement.setString(2, "Cientifico " + i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Datos insertados en la tabla 'CIENTIFICOS'");

            String createProyectoTableSQL = "CREATE TABLE PROYECTO (" +
                    "id CHAR(4) PRIMARY KEY, " +
                    "Nombre NVARCHAR(255) NOT NULL, " +
                    "Horas INT)";
            statement.executeUpdate(createProyectoTableSQL);
            System.out.println("Tabla 'PROYECTO' creada");

            String insertProyectoSQL = "INSERT INTO PROYECTO (id, Nombre, Horas) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertProyectoSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "P" + i);
                    preparedStatement.setString(2, "Proyecto " + i);
                    preparedStatement.setInt(3, 100 * i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Datos insertados en la tabla 'PROYECTO'");

            String createAsignadoATableSQL = "CREATE TABLE ASIGNADO_A (" +
                    "Cientifico VARCHAR(8), " +
                    "Proyecto CHAR(4), " +
                    "PRIMARY KEY (Cientifico, Proyecto), " +
                    "FOREIGN KEY (Cientifico) REFERENCES CIENTIFICOS(DNI), " +
                    "FOREIGN KEY (Proyecto) REFERENCES PROYECTO(id))";
            statement.executeUpdate(createAsignadoATableSQL);
            System.out.println("Tabla 'ASIGNADO_A' creada");

            String insertAsignadoASQL = "INSERT INTO ASIGNADO_A (Cientifico, Proyecto) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertAsignadoASQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "DNI" + i);
                    preparedStatement.setString(2, "P" + i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Datos insertados en la tabla 'ASIGNADO_A'");

            System.out.println("Base de datos creada y registros insertados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
