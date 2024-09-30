package org.example.DAO;

import org.example.entity.Persona;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAODerby implements PersonaDAO{

    private Connection conn;

    public PersonaDAODerby(Connection conn) {
        this.conn = conn;

        try {
            createTable();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createTable() throws SQLException {
        // Verificar si la tabla existe
        DatabaseMetaData dbm = conn.getMetaData();
        ResultSet tables = dbm.getTables(null, null, "PERSONA", null);

        if (!tables.next()) {  // Si no existe, la creamos
            String table = "CREATE TABLE Persona(" +
                    "id INT," +
                    "nombre VARCHAR(500)," +
                    "edad INT," +
                    "PRIMARY KEY(id))";
            PreparedStatement ps = conn.prepareStatement(table);
            ps.executeUpdate();
            ps.close();
            conn.commit();
        } else {
            System.out.println("La tabla Persona ya existe.");
        }
    }

    /*
    * Este método verifica primero si una persona con el id dado ya existe en la base de datos.
    * Si no existe, la agrega; si ya existe, imprime un mensaje indicando que no se puede agregar.
    * Esta verificación evita que se inserten registros duplicados, cumpliendo así con las restricciones
    * de clave primaria (id) en la tabla Persona.
    */
    @Override
    public void addPerson(int id, String name, int age) throws SQLException {
        String checkIdExists = "SELECT 1 FROM Persona WHERE id = ?";  //con la consulta sql se verifica si hay una fila con el mismo id
        PreparedStatement ps = conn.prepareStatement(checkIdExists); /*PreparedStatement: Se usa PreparedStatement para prevenir inyecciones SQL y establecer el valor del id en la consulta con ps.setInt(1, id), donde 1 es el índice del parámetro en la consulta.*/
        ps.setInt(1, id);
        ResultSet rs = ps.executeQuery(); //executeQuery():Ejecuta la consulta y devuelve un ResultSet (el conjunto de resultados).

        if (!rs.next()) {  // Si no devuelve resultados, el ID no existe //rs.next(): Avanza al siguiente resultado en el ResultSet.
            String insert = "INSERT INTO Persona (id, nombre, edad) VALUES (?, ?, ?)";
            ps = conn.prepareStatement(insert);  //PreparedStatement: Se reemplazan los ? en la consulta con los valores del id, nombre, y edad que se reciben como parámetros en el método.
            ps.setInt(1, id);
            ps.setString(2, name);
            ps.setInt(3, age);
            ps.executeUpdate();   //executeUpdate(): Ejecuta la consulta de inserción, añadiendo la nueva persona a la tabla.
        } else {
            System.out.println("Persona con ID " + id + " ya existe.");
        }

        /*rs.close() y ps.close(): Se cierran el ResultSet y el PreparedStatement para liberar los recursos que fueron utilizados para realizar la consulta y la inserción.*/
        rs.close();
        ps.close();
    }

    @Override
    public List<Persona> getAllPersons() throws SQLException {
        List<Persona> persons = new ArrayList<>();
        String query = "SELECT * FROM Persona";
        PreparedStatement ps = conn.prepareStatement(query);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            persons.add(new Persona(rs.getInt("id"), rs.getString("nombre"), rs.getInt("edad")));
        }

        rs.close();
        ps.close();
        return persons;
    }
}

/*  metodo que primero borra la tabla y despues la vuelve a crear
* public void createTable() throws SQLException {
    try {
        // Intentar eliminar la tabla si existe
        String dropTable = "DROP TABLE Persona";
        PreparedStatement psDrop = conn.prepareStatement(dropTable);
        psDrop.executeUpdate();
        psDrop.close();
        System.out.println("Tabla 'Persona' eliminada exitosamente.");
    } catch (SQLException e) {
        // Si la tabla no existe, atrapamos la excepción y continuamos
        System.out.println("La tabla 'Persona' no existía, no se eliminó.");
    }

    // Crear la tabla de nuevo
    String createTable = "CREATE TABLE Persona(" +
                         "id INT," +
                         "nombre VARCHAR(500)," +
                         "edad INT," +
                         "PRIMARY KEY(id))";
    PreparedStatement psCreate = conn.prepareStatement(createTable);
    psCreate.executeUpdate();
    psCreate.close();
    System.out.println("Tabla 'Persona' creada exitosamente.");
}*/