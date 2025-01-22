package DAO;

import Entity.DonDatPhong;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class DonDatPhongDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<DonDatPhong> getDanhSachDonDatPhong() {
        List<DonDatPhong> list = null;
        try {
            list = em.createQuery("SELECT ddp FROM DonDatPhong ddp", DonDatPhong.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public DonDatPhong getDonDatPhong(String ma) {
        DonDatPhong ddp = null;
        try {
            ddp = em.find(DonDatPhong.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ddp;
    }
    public boolean update(DonDatPhong ddp) {
        try {
            em.getTransaction().begin();
            em.merge(ddp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(DonDatPhong ddp) {
        try {
            em.getTransaction().begin();
            em.persist(ddp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(DonDatPhong ddp) {
        try {
            em.getTransaction().begin();
            em.remove(ddp);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
