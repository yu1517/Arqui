package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.example.entity.Socio;

import java.util.List;

public class SocioDAO implements DAO<Socio> {
    private EntityManagerFactory emf;

    public SocioDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void insert(Socio socio) {
        EntityManager em = emf.createEntityManager();
        if (socio == null) {
            throw new IllegalArgumentException("La entidad Socio no puede ser null.");
        }
        EntityTransaction transaction = em.getTransaction();
        try {
            transaction.begin();
            em.merge(socio);
            transaction.commit();
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
    public Socio selectById(int id) {
        EntityManager em = emf.createEntityManager();
        em.close();
        return em.find(Socio.class, id);

    }

    @Override
    public List<Socio> selectAll() {
        EntityManager em = emf.createEntityManager();
        em.close();
        return em.createQuery("SELECT s FROM Socio s", Socio.class).getResultList();
    }

    @Override
    public boolean update(Socio socio) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Socio existingSocio = em.find(Socio.class, socio.getId());

            if (existingSocio != null) {
                //Actualizar los campos simples
                existingSocio.setPersona(socio.getPersona());
                existingSocio.setTipo(socio.getTipo());
                em.merge(existingSocio);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false; // No se encontró el socio para actualizar
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
            Socio socio = em.find(Socio.class, id);

            if (socio != null) {
                em.remove(socio);
                transaction.commit();
                return true;
            } else {
                transaction.rollback();
                return false; // No se encontró el socio para eliminar
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
