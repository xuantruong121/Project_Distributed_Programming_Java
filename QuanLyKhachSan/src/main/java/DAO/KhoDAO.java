package DAO;

import Entity.Kho;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class KhoDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<Kho> getDanhSachKho() {
        List<Kho> list = null;
        try {
            list = em.createQuery("SELECT k FROM Kho k", Kho.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public Kho getKho(String ma) {
        Kho k = null;
        try {
            k = em.find(Kho.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return k;
    }
    public boolean update(Kho k) {
        try {
            em.getTransaction().begin();
            em.merge(k);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(Kho k) {
        try {
            em.getTransaction().begin();
            em.persist(k);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(Kho k) {
        try {
            em.getTransaction().begin();
            em.remove(k);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
