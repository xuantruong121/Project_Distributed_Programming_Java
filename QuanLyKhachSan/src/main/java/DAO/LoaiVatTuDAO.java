package DAO;

import Entity.LoaiVatTu;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class LoaiVatTuDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<LoaiVatTu> getDanhSachLoaiVatTu() {
        List<LoaiVatTu> list = null;
        try {
            list = em.createQuery("SELECT lvt FROM LoaiVatTu lvt", LoaiVatTu.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public LoaiVatTu getLoaiVatTu(String ma) {
        LoaiVatTu lvt = null;
        try {
            lvt = em.find(LoaiVatTu.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lvt;
    }
    public boolean update(LoaiVatTu lvt) {
        try {
            em.getTransaction().begin();
            em.merge(lvt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(LoaiVatTu lvt) {
        try {
            em.getTransaction().begin();
            em.persist(lvt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(LoaiVatTu lvt) {
        try {
            em.getTransaction().begin();
            em.remove(lvt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
