package org.example.factory;

import org.example.DAO.PersonaDAO;
import org.example.DAO.PersonaDAODerby;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DerbyDAOFactory extends DAOFactory {
    public static final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    public static final String DB_URL = "jdbc:derby:MyDB;create=true";
    ;

    public static Connection createConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(DB_URL);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PersonaDAO getPersonaDAO() {
        return new PersonaDAODerby(createConnection());
    }
}
