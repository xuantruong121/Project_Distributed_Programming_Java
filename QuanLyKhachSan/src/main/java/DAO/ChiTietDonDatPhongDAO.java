package DAO;

import Entity.ChiTietDonDatPhong;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChiTietDonDatPhongDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<ChiTietDonDatPhong> getDanhSachChiTietDonDatPhong() {
        List<ChiTietDonDatPhong> list = null;
        try {
            list = em.createQuery("SELECT ctddp FROM ChiTietDonDatPhong ctddp", ChiTietDonDatPhong.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ChiTietDonDatPhong getChiTietDonDatPhong(String ma) {
        ChiTietDonDatPhong ctddp = null;
        try {
            ctddp = em.find(ChiTietDonDatPhong.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctddp;
    }
    public boolean update(ChiTietDonDatPhong ctddp) {
        try {
            em.getTransaction().begin();
            em.merge(ctddp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(ChiTietDonDatPhong ctddp) {
        try {
            em.getTransaction().begin();
            em.persist(ctddp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(ChiTietDonDatPhong ctddp) {
        try {
            em.getTransaction().begin();
            em.remove(ctddp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

}
