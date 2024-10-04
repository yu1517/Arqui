package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.example.entity.Direccion;
import org.example.entity.Persona;

import java.util.List;

public class PersonaDAO implements DAO<Persona> {
    private EntityManagerFactory emf;

    public PersonaDAO(EntityManagerFactory emf) {
        super();
        this.emf = emf;
    }

    @Override
    public void insert(Persona persona) {
        EntityManager em = emf.createEntityManager();
        if (persona == null) {
            throw new IllegalArgumentException("La entidad Persona no puede ser null.");
        }

        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(persona);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Lanzas la excepción en lugar de devolver false
        }
        finally {
            em.close();
        }
    }

    @Override
    public Persona selectById(int id) {
        EntityManager em = emf.createEntityManager();
        em.close();
        return em.find(Persona.class, id);
    }

    @Override
    public List<Persona> selectAll() {
        EntityManager em = emf.createEntityManager();
        List<Persona> resultado = em.createQuery("FROM Persona", Persona.class).getResultList();
        em.close();
        return resultado;
    }

    @Override
    public boolean update(Persona persona) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Persona existingPersona = em.find(Persona.class, persona.getId());

            if (existingPersona != null) {
                // Actualizar campos simples
                existingPersona.setNombre(persona.getNombre());
                existingPersona.setEdad(persona.getEdad());

                // Verificar si el domicilio ha cambiado antes de actualizarlo
                if (persona.getDomicilio() != null && !persona.getDomicilio().equals(existingPersona.getDomicilio())) {
                    Direccion newDomicilio = em.find(Direccion.class, persona.getDomicilio().getId());
                    existingPersona.setDomicilio(newDomicilio);
                }

                // Guardar cambios
                em.merge(existingPersona);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false; // Si no se encuentra la persona
            }
        } catch (Exception e) {
            transaction.rollback();
            throw e; // Podrías manejar la excepción aquí o volver a lanzarla
        }
        finally {
            em.close();
        }
    }

    @Override
    public boolean delete(int id) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Persona persona = em.find(Persona.class, id);

            if (persona != null) {
                em.remove(persona);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false;
            }
        } catch (PersistenceException e) {
            transaction.rollback();
            throw e;
        }
        finally {
            em.close();
        }
    }
}
