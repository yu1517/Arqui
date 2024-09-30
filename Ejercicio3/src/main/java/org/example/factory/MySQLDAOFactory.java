package org.example.factory;

import org.example.DAO.PersonaDAO;
import org.example.DAO.PersonaDAOMySQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLDAOFactory extends DAOFactory {
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String DB_URL = "jdbc:mysql://localhost:3306/dbArqui";

    public static Connection createConnection() {
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(DB_URL, "root", "rp");
        } catch (SQLException | ClassNotFoundException e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public PersonaDAO getPersonaDAO() {
        return new PersonaDAOMySQL(createConnection());
    }
}
