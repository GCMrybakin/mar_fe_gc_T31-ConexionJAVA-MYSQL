import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio9 {
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

            String createFacultadTableSQL = "CREATE TABLE FACULTAD (" +
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Nombre NVARCHAR(100) NOT NULL)";
            statement.executeUpdate(createFacultadTableSQL);
            System.out.println("Tabla 'FACULTAD' creada con éxito.");

            String insertFacultadSQL = "INSERT INTO FACULTAD (Nombre) VALUES (?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertFacultadSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Facultad " + i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'FACULTAD'.");

            String createEquiposTableSQL = "CREATE TABLE EQUIPOS (" +
                    "NumSerie CHAR(4) PRIMARY KEY, " +
                    "Nombre NVARCHAR(100) NOT NULL, " +
                    "Facultad INT NOT NULL, " +
                    "FOREIGN KEY (Facultad) REFERENCES FACULTAD(Codigo))";
            statement.executeUpdate(createEquiposTableSQL);
            System.out.println("Tabla 'EQUIPOS' creada con éxito.");

            String insertEquiposSQL = "INSERT INTO EQUIPOS (NumSerie, Nombre, Facultad) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertEquiposSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "E" + i);
                    preparedStatement.setString(2, "Equipo " + i);
                    preparedStatement.setInt(3, i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'EQUIPOS'.");

            String createInvestigadoresTableSQL = "CREATE TABLE INVESTIGADORES (" +
                    "DNI VARCHAR(8) PRIMARY KEY, " +
                    "NomApels NVARCHAR(255) NOT NULL, " +
                    "Facultad INT NOT NULL, " +
                    "FOREIGN KEY (Facultad) REFERENCES FACULTAD(Codigo))";
            statement.executeUpdate(createInvestigadoresTableSQL);
            System.out.println("Tabla 'INVESTIGADORES' creada con éxito.");

            String insertInvestigadoresSQL = "INSERT INTO INVESTIGADORES (DNI, NomApels, Facultad) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertInvestigadoresSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "DNI" + i);
                    preparedStatement.setString(2, "Investigador " + i);
                    preparedStatement.setInt(3, i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'INVESTIGADORES'.");

            String createReservaTableSQL = "CREATE TABLE RESERVA (" +
                    "DNI VARCHAR(8), " +
                    "NumSerie CHAR(4), " +
                    "Comienzo DATETIME, " +
                    "Fin DATETIME, " +
                    "PRIMARY KEY (DNI, NumSerie), " +
                    "FOREIGN KEY (DNI) REFERENCES INVESTIGADORES(DNI), " +
                    "FOREIGN KEY (NumSerie) REFERENCES EQUIPOS(NumSerie))";
            statement.executeUpdate(createReservaTableSQL);
            System.out.println("Tabla 'RESERVA' creada con éxito.");

            System.out.println("Base de datos creada y registros insertados correctamente.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
