package DAO;

import Entity.HoaDon;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class HoaDonDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<HoaDon> getDanhSachHoaDon() {
        List<HoaDon> list = null;
        try {
            list = em.createQuery("SELECT hd FROM HoaDon hd", HoaDon.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public HoaDon getHoaDon(String ma) {
        HoaDon hd = null;
        try {
            hd = em.find(HoaDon.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hd;
    }
    public boolean update(HoaDon hd) {
        try {
            em.getTransaction().begin();
            em.merge(hd);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(HoaDon hd) {
        try {
            em.getTransaction().begin();
            em.persist(hd);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(HoaDon hd) {
        try {
            em.getTransaction().begin();
            em.remove(hd);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
