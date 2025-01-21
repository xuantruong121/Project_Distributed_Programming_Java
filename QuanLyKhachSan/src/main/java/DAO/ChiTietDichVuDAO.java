package DAO;

import Entity.ChiTietDichVu;

import jakarta.persistence.EntityManager;

import java.util.List;

public class ChiTietDichVuDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<ChiTietDichVu> getDanhSachChiTietDichVu() {
        List<ChiTietDichVu> list = null;
        try {
            list = em.createQuery("SELECT ctdv FROM ChiTietDichVu ctdv", ChiTietDichVu.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ChiTietDichVu getChiTietDichVu(String ma) {
        ChiTietDichVu ctdv = null;
        try {
            ctdv = em.find(ChiTietDichVu.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctdv;
    }
    public boolean update(ChiTietDichVu ctdv) {
        try {
            em.getTransaction().begin();
            em.merge(ctdv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(ChiTietDichVu ctdv) {
        try {
            em.getTransaction().begin();
            em.persist(ctdv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(ChiTietDichVu ctdv) {
        try {
            em.getTransaction().begin();
            em.remove(ctdv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
