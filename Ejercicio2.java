import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class Ejercicio2 {
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

            String createDepartamentosTableSQL = "CREATE TABLE DEPARTAMENTOS (" +
                    "Codigo INT AUTO_INCREMENT PRIMARY KEY, " +
                    "Nombre NVARCHAR(100) NOT NULL, " +
                    "Presupuesto INT NOT NULL)";
            statement.executeUpdate(createDepartamentosTableSQL);
            System.out.println("Tabla 'DEPARTAMENTOS' creada con éxito.");

            String insertDepartamentosSQL = "INSERT INTO DEPARTAMENTOS (Nombre, Presupuesto) VALUES (?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertDepartamentosSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "Departamento " + i);
                    preparedStatement.setInt(2, 10000 * i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'DEPARTAMENTOS'.");

            String createEmpleadosTableSQL = "CREATE TABLE EMPLEADOS (" +
                    "DNI VARCHAR(8) PRIMARY KEY, " +
                    "Nombre NVARCHAR(100) NOT NULL, " +
                    "Apellidos NVARCHAR(255) NOT NULL, " +
                    "Departamento INT NOT NULL, " +
                    "FOREIGN KEY (Departamento) REFERENCES DEPARTAMENTOS(Codigo))";
            statement.executeUpdate(createEmpleadosTableSQL);
            System.out.println("Tabla 'EMPLEADOS' creada con éxito.");

            String insertEmpleadosSQL = "INSERT INTO EMPLEADOS (DNI, Nombre, Apellidos, Departamento) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = conexion.prepareStatement(insertEmpleadosSQL)) {
                for (int i = 1; i <= 5; i++) {
                    preparedStatement.setString(1, "DNI" + i);
                    preparedStatement.setString(2, "Empleado " + i);
                    preparedStatement.setString(3, "Apellido " + i);
                    preparedStatement.setInt(4, i);
                    preparedStatement.executeUpdate();
                }
            }
            System.out.println("Registros insertados en la tabla 'EMPLEADOS'.");

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
