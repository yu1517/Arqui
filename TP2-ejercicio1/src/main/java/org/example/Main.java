package org.example;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.dao.*;
import org.example.entity.Direccion;
import org.example.entity.Persona;
import org.example.entity.Socio;
import org.example.entity.Turno;
import org.example.factory.AbstractFactory;

import java.sql.Timestamp;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Main {
    public static void main(String[] args) {
        Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);
        AbstractFactory af = AbstractFactory.getDAOFactory(1);

        try {
            // Crear instancias de los DAO
            assert af != null;
            DAO<Persona> personaDAO = af.getPersonaDAO();
            DAO<Direccion> direccionDAO = af.getDireccionDAO();
            DAO<Turno> turnoDAO = af.getTurnoDAO();
            DAO<Socio> socioDAO = af.getSocioDAO();

            // Ejemplo de inserción de una nueva dirección
            Direccion direccion = new Direccion("Tandil", "Una calle de Tandil");
            direccionDAO.insert(direccion);

            // Ejemplo de inserción de una nueva persona
            Persona persona = new Persona("Juan", 30,direccion);
            personaDAO.insert(persona);

            // Ejemplo de inserción de un socio
            Socio socio = new Socio(persona, "Socio");
            socioDAO.insert(socio);

            // Ejemplo de creación de un turno
            Turno turno = new Turno(new Timestamp(System.currentTimeMillis()));
            turno.getJugadores().add(persona);
            turnoDAO.insert(turno);

            // Ejemplo de selección de todas las personas
            List<Persona> personas = personaDAO.selectAll();
            personas.forEach(System.out::println);

            // Ejemplo de actualización de una persona
            persona.setNombre("Juan Carlos");
            personaDAO.update(persona);

            // Ejemplo de eliminación de un socio
            //socioDAO.delete(socio.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}