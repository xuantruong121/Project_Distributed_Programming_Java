package DAO;

import Entity.LoaiPhuThu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LoaiPhuThuDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<LoaiPhuThu> getDanhSachLoaiPhuThu() {
        List<LoaiPhuThu> list = null;
        try {
            list = em.createQuery("SELECT lpt FROM LoaiPhuThu lpt", LoaiPhuThu.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public LoaiPhuThu getLoaiPhuThu(String ma) {
        LoaiPhuThu lpt = null;
        try {
            lpt = em.find(LoaiPhuThu.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lpt;
    }
    public boolean update(LoaiPhuThu lpt) {
        try {
            em.getTransaction().begin();
            em.merge(lpt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(LoaiPhuThu lpt) {
        try {
            em.getTransaction().begin();
            em.persist(lpt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(LoaiPhuThu lpt) {
        try {
            em.getTransaction().begin();
            em.remove(lpt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
