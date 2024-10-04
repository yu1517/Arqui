package org.example.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.PersistenceException;
import org.example.entity.Direccion;

import java.util.List;

public class DireccionDAO implements DAO<Direccion> {
    private EntityManagerFactory emf;

    public DireccionDAO(EntityManagerFactory emf) {
        this.emf = emf;
    }

    @Override
    public void insert(Direccion direccion) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            em.persist(direccion);
            transaction.commit();
        } catch (PersistenceException e) {
            transaction.rollback();
            throw e;
        }
        finally {
            em.close();
        }
    }

    @Override
    public Direccion selectById(int id) {
        EntityManager em = emf.createEntityManager();
        em.close();
        return em.find(Direccion.class, id);
    }

    @Override
    public List<Direccion> selectAll() {
        EntityManager em = emf.createEntityManager();
        em.close();
        return em.createQuery("SELECT d FROM Direccion d", Direccion.class).getResultList();
    }

    @Override
    public boolean update(Direccion direccion) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();
        transaction.begin();

        try {
            Direccion existingDireccion = em.find(Direccion.class, direccion.getId());

            if (existingDireccion != null) {
                // Actualiza los campos
                existingDireccion.setCiudad(direccion.getCiudad());
                existingDireccion.setCalle(direccion.getCalle());

                em.merge(existingDireccion);
                transaction.commit();
                return true; // Indica que la actualización fue exitosa
            } else {
                transaction.rollback();
                return false; // No se encontró la dirección para actualizar
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
            Direccion direccion = em.find(Direccion.class, id);

            if (direccion != null) {
                em.remove(direccion);
                transaction.commit();
                return true; // Indica que la eliminación fue exitosa
            } else {
                transaction.rollback();
                return false; // No se encontró la dirección para eliminar
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
