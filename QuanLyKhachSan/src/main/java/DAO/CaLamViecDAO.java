package DAO;

import Entity.CaLamViec;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class CaLamViecDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<CaLamViec> getDanhSachCaLamViec() {
        List<CaLamViec> list = null;
        try {
            list = em.createQuery("SELECT clv FROM CaLamViec clv", CaLamViec.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public CaLamViec getCaLamViec(String ma) {
        CaLamViec clv = null;
        try {
            clv = em.find(CaLamViec.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clv;
    }
    public boolean update(CaLamViec clv) {
        try {
            em.getTransaction().begin();
            em.merge(clv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(CaLamViec clv) {
        try {
            em.getTransaction().begin();
            em.persist(clv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(CaLamViec clv) {
        try {
            em.getTransaction().begin();
            em.remove(clv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
