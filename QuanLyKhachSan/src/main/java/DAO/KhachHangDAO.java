package DAO;

import Entity.KhachHang;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class KhachHangDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<KhachHang> getDanhSachKhachHang() {
        List<KhachHang> list = null;
        try {
            list = em.createQuery("SELECT kh FROM KhachHang kh", KhachHang.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public KhachHang getKhachHang(String ma) {
        KhachHang kh = null;
        try {
            kh = em.find(KhachHang.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return kh;
    }
    public boolean update(KhachHang kh) {
        try {
            em.getTransaction().begin();
            em.merge(kh);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(KhachHang kh) {
        try {
            em.getTransaction().begin();
            em.persist(kh);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(KhachHang kh) {
        try {
            em.getTransaction().begin();
            em.remove(kh);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
