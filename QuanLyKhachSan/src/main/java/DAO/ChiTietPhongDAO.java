package DAO;

import Entity.ChiTietPhong;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class ChiTietPhongDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<ChiTietPhong> getDanhSachChiTietPhong() {
        List<ChiTietPhong> list = null;
        try {
            list = em.createQuery("SELECT ctp FROM ChiTietPhong ctp", ChiTietPhong.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ChiTietPhong getChiTietPhong(String ma) {
        ChiTietPhong ctp = null;
        try {
            ctp = em.find(ChiTietPhong.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctp;
    }
    public boolean update(ChiTietPhong ctp) {
        try {
            em.getTransaction().begin();
            em.merge(ctp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(ChiTietPhong ctp) {
        try {
            em.getTransaction().begin();
            em.persist(ctp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(ChiTietPhong ctp) {
        try {
            em.getTransaction().begin();
            em.remove(ctp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
