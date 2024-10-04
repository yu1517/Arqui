package org.example.factory;

import org.example.dao.DAO;
import org.example.entity.Direccion;
import org.example.entity.Persona;
import org.example.entity.Socio;
import org.example.entity.Turno;

public abstract class AbstractFactory {
    public static final int MYSQL_DB = 1;
    //public static final int DERBY_DB = 2;

    public abstract DAO<Direccion> getDireccionDAO();
    public abstract DAO<Persona> getPersonaDAO();
    public abstract DAO<Turno> getTurnoDAO();
    public abstract DAO<Socio> getSocioDAO();
    public abstract void closeEntityManagerFactory();

    public static AbstractFactory getDAOFactory(int whichFactory) {
        switch (whichFactory) {
            case MYSQL_DB:
                return new ConexionMySQL();
            default:
                return null;
        }
    }
}
