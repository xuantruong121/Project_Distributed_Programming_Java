package DAO;

import Entity.LoaiPhong;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class LoaiPhongDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<LoaiPhong> getDanhSachLoaiPhong() {
        List<Entity.LoaiPhong> list = null;
        try {
            list = em.createQuery("SELECT lp FROM LoaiPhong lp", Entity.LoaiPhong.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public LoaiPhong getLoaiPhong(String ma) {
        LoaiPhong lp = null;
        try {
            lp = em.find(LoaiPhong.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lp;
    }
    public boolean update(LoaiPhong lp) {
        try {
            em.getTransaction().begin();
            em.merge(lp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(LoaiPhong lp) {
        try {
            em.getTransaction().begin();
            em.persist(lp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(LoaiPhong lp) {
        try {
            em.getTransaction().begin();
            em.remove(lp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
