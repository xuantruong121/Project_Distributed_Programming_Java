package DAO;

import Entity.LoaiDichVu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LoaiDichVuDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<LoaiDichVu> getDanhSachLoaiDichVu() {
        List<LoaiDichVu> list = null;
        try {
            list = em.createQuery("SELECT ldv FROM LoaiDichVu ldv", LoaiDichVu.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public LoaiDichVu getLoaiDichVu(String ma) {
        LoaiDichVu ldv = null;
        try {
            ldv = em.find(LoaiDichVu.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ldv;
    }
    public boolean update(LoaiDichVu ldv) {
        try {
            em.getTransaction().begin();
            em.merge(ldv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(LoaiDichVu ldv) {
        try {
            em.getTransaction().begin();
            em.persist(ldv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(LoaiDichVu ldv) {
        try {
            em.getTransaction().begin();
            em.remove(ldv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

}
