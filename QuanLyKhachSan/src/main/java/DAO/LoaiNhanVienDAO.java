package DAO;

import Entity.LoaiNhanVien;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LoaiNhanVienDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<LoaiNhanVien> getDanhSachLoaiNhanVien() {
        List<LoaiNhanVien> list = null;
        try {
            list = em.createQuery("SELECT lnv FROM LoaiNhanVien lnv", LoaiNhanVien.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public LoaiNhanVien getLoaiNhanVien(String ma) {
        LoaiNhanVien lnv = null;
        try {
            lnv = em.find(LoaiNhanVien.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lnv;
    }
    public boolean update(LoaiNhanVien lnv) {
        try {
            em.getTransaction().begin();
            em.merge(lnv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(LoaiNhanVien lnv) {
        try {
            em.getTransaction().begin();
            em.persist(lnv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(LoaiNhanVien lnv) {
        try {
            em.getTransaction().begin();
            em.remove(lnv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
