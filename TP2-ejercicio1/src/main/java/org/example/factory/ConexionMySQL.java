package org.example.factory;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.dao.*;
import org.example.entity.Socio;
import org.example.entity.Turno;

public class ConexionMySQL extends AbstractFactory{

    private static final String PERSISTENCE_UNIT_NAME = "Example";
    private EntityManagerFactory emf;

    public ConexionMySQL() {
        this.emf = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
    }

    /*Metodo auxiliar para obtener un nuevo entityManager*/
    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    @Override
    public DAO getDireccionDAO() {
        return new DireccionDAO(emf);
    }

    public DAO getPersonaDAO(){
        return new PersonaDAO(emf);
    }

    @Override
    public DAO<Turno> getTurnoDAO() {
        return new TurnoDAO(emf);
    }

    @Override
    public DAO<Socio> getSocioDAO() {;
        return new SocioDAO(emf);
    }

    public void closeEntityManagerFactory() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
