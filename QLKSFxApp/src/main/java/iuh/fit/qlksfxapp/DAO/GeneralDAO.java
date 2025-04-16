package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;

import java.util.List;

public class GeneralDAO {
    private static final EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();

    public GeneralDAO() {
        // Không tạo EntityManager trong constructor
    }

    public <T> boolean addOb(T ob) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.persist(ob);
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

    public <T> boolean updateOb(T ob) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            em.merge(ob);
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

    public <T> boolean deleteOb(T ob) {
        EntityManager em = null;
        try {
            em = emf.createEntityManager();
            em.getTransaction().begin();
            // Nếu đối tượng là detached, cần merge trước khi xóa
            T managedEntity = (T) em.merge(ob);
            em.remove(managedEntity);
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

    public <T> T findOb(Class<T> entityClass, Object id) {
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

    // Add a method to get a list of all entities of a type
    public <T> List<T> findAll(Class<T> entityClass) {
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
