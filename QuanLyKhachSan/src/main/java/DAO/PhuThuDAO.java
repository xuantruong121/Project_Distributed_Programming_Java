package DAO;

import Entity.PhuThu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PhuThuDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<PhuThu> getDanhSachPhuThu() {
        List<PhuThu> list = null;
        try {
            list = em.createQuery("SELECT pt FROM PhuThu pt", PhuThu.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public PhuThu getPhuThu(String ma) {
        PhuThu pt = null;
        try {
            pt = em.find(PhuThu.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return pt;
    }
    public boolean update(PhuThu pt) {
        try {
            em.getTransaction().begin();
            em.merge(pt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(PhuThu pt) {
        try {
            em.getTransaction().begin();
            em.persist(pt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(PhuThu pt) {
        try {
            em.getTransaction().begin();
            em.remove(pt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
