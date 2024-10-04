package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.example.entity.Turno;

import java.util.List;


public class TurnoDAO implements DAO<Turno> {
    private EntityManagerFactory emf;

    public TurnoDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void insert(Turno turno) {
        EntityManager em = emf.createEntityManager();
        if (turno == null) {
            throw new IllegalArgumentException("La entidad Turno no puede ser null.");
        }
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.persist(turno);
            transaction.commit();
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e;
        }
        finally {
            em.close();
        }
    }

    @Override
    public Turno selectById(int id) {
        EntityManager em = emf.createEntityManager();
        em.close();
        return em.find(Turno.class, id);
    }

    @Override
    public List<Turno> selectAll() {
        EntityManager em = emf.createEntityManager();
        em.close();
        return em.createQuery("SELECT t FROM Turno t", Turno.class).getResultList();
    }

    @Override
    public boolean update(Turno turno) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Turno existingTurno = em.find(Turno.class, turno.getId());

            if (existingTurno != null) {
                existingTurno.setFecha(turno.getFecha());
                existingTurno.setJugadores(turno.getJugadores());
                em.merge(existingTurno);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false; // No se encontró el turno para actualizar
            }
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Manejo de la excepción
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
            Turno turno = em.find(Turno.class, id);

            if (turno != null) {
                em.remove(turno);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false; // No se encontró el turno para eliminar
            }
        } catch (PersistenceException e) {
            if (transaction.isActive()) {
                transaction.rollback();
            }
            throw e; // Manejo de la excepción
        }
        finally {
            em.close();
        }
    }
}
