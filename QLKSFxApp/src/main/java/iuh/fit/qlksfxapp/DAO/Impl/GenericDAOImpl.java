package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.GenericDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

/**
 * Generic DAO implementation for basic CRUD operations
 * @param <T> The entity type
 * @param <ID> The ID type
 */
public class GenericDAOImpl<T, ID> implements GenericDAO<T, ID> {
    private static final EntityManagerFactory emf = EntityManagerUtilImpl.getEntityManagerFactory();
    private final Class<T> entityClass;
    
    public GenericDAOImpl(Class<T> entityClass) {
        this.entityClass = entityClass;
    }
    
    @Override
    public boolean save(T t) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(t);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    @Override
    public boolean update(T t) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(t);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    @Override
    public T findById(ID id) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.find(entityClass, id);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    
    @Override
    public List<T> getAll() {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                    .getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
