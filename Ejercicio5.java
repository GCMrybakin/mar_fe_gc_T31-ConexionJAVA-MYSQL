import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio5 {
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

            String createDespachosTableSQL = "CREATE TABLE DESPACHOS (" +
            		"Numero INT PRIMARY KEY, " +
            		"capacidad INT NOT NULL)";
            statement.executeUpdate(createDespachosTableSQL);
            System.out.println("Tabla 'Despachos' creada");
            
            String insertDespachosSQL = "INSERT INTO DESPACHOS (Numero, capacidad) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertDespachosSQL)) {
            	for (int i = 1; i <= 5; i++) {
            		preparedStatement.setInt(1, i);
            		preparedStatement.setInt(2, 10 + i);
            		preparedStatement.executeUpdate();
            	}
            }
            System.out.println("Datos insertados a 'Despacho'");
            
            String createDirectoresTableSQL = "CREATE TABLE DIRECTORES (" +
                    "DNI VARCHAR(8) PRIMARY KEY, " +
                    "NomApels NVARCHAR(255) NOT NULL, " +
                    "DNIJefe VARCHAR(8), " +
                    "Despacho INT, " +
                    "FOREIGN KEY (DNIJefe) REFERENCES DIRECTORES(DNI), " +
                    "FOREIGN KEY (Despacho) REFERENCES DESPACHOS(Numero))";
            statement.executeUpdate(createDirectoresTableSQL);
            System.out.println("Tabla 'Directores' creada");

            String insertDirectoresSQL = "INSERT INTO DIRECTORES (DNI, NomApels, DNIJefe, Despacho) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertDirectoresSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "DNI" + i);
                    preparedStatement.setString(2, "Director " + i);
                    if (i > 1) {
                        preparedStatement.setString(3, "DNI" + (i - 1));
                    } else {
                        preparedStatement.setString(3, null);
                    }
                    preparedStatement.setInt(4, i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Datos insertados a 'Directores'");


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