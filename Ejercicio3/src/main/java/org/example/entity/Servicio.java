package org.example.entity;

import org.apache.derby.iapi.sql.ResultSet;
import org.example.DAO.PersonaDAO;
import org.example.factory.DAOFactory;

import java.sql.SQLException;
import java.util.List;

public class Servicio {
    private PersonaDAO personaDAO;

    public Servicio(DAOFactory factory) {
        this.personaDAO = factory.getPersonaDAO();
    }

    public void addPerson(int id, String name, int age) throws SQLException {
        personaDAO.addPerson(id, name, age);
    }

    public List<Persona> getAllPersons() throws SQLException {
        return personaDAO.getAllPersons();
    }
}
