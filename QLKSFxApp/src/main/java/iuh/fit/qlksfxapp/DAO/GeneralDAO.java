package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GeneralDAO {
    private static EntityManagerFactory emf;
    private EntityManager em;

    public GeneralDAO() {
        emf = EntityManagerUtil.getEntityManagerFactory();
        em = emf.createEntityManager();
    }

    // Close the EntityManager when done
    public void close() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }

    public <T> boolean addOb(T ob) {
        try {
            em.getTransaction().begin();
            em.persist(ob);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public <T> boolean updateOb(T ob) {
        try {
            em.getTransaction().begin();
            em.merge(ob);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public <T> boolean deleteOb(T ob) {
        try {
            em.getTransaction().begin();
            em.remove(ob);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }

    public <T> T findOb(Class<T> entityClass, Object id) {
        return em.find(entityClass, id);
    }

    // Add a method to get a list of all entities of a type
    public <T> List<T> findAll(Class<T> entityClass) {
        return em.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
                .getResultList();
    }
}
