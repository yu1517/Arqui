package org.example;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class baseDatos {
    public static void main(String[] args){
        //registrar el driver
        String driver = "com.mysql.cj.jdbc.Driver";

        //
        try {
            Class.forName(driver).getDeclaredConstructor().newInstance();  //pedimos el driver, el contructor y una nueva intancia del driver
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
            throw new RuntimeException();
        }

        /* para conectarnos a la bd, definimos
        la direccion de la base de datos (uri)
         */
        String uri = "jdbc:mysql://localhost:3306/dbArqui";

        //definida la uri, creamos la conexion
        try {
            Connection conn = DriverManager.getConnection(uri,"root", "rp");
            conn.setAutoCommit(false);
            createTables(conn); //llamamos al metodo crear tablas
            addPerson(conn,1,"Pedro", 20); //llamamos al metodo agregar persona con los parametros correspondientes
            addPerson(conn,2,"Maria", 25);
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static void addPerson(Connection conn, int id, String name, int years) throws SQLException {
        String insert = "INSERT INTO persona (id, nombre, edad) VALUES (?,?,?)";  //agregamos personas con insert
        PreparedStatement ps = conn.prepareStatement(insert);  //asegura que lo que se agrega son valores validos
        ps.setInt(1,id);  //seteamos los parametros 1, 2 y 3
        ps.setString(2,name);
        ps.setInt(3, years);
        ps.executeUpdate();  //ejecutamos la intruccion de update
        ps.close();  //cerramos el preparedstatement
        conn.commit();  //hacemos el commit con la insercion
    }

    private static void createTables(Connection conn) throws SQLException {
        String table = "CREATE TABLE persona("+
                "id INT," +
                "nombre VARCHAR(500)," +
                "edad INT," +
                "PRIMARY KEY(id))";
        conn.prepareStatement(table).execute();
        conn.commit();
    }
}
