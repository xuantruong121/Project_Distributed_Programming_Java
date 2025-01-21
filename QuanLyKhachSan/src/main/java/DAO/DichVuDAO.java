package DAO;

import Entity.DichVu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DichVuDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<DichVu> getDanhSachDichVu() {
        List<DichVu> list = null;
        try {
            list = em.createQuery("SELECT dv FROM DichVu dv", DichVu.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public DichVu getDichVu(String ma) {
        DichVu dv = null;
        try {
            dv = em.find(DichVu.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dv;
    }
    public boolean update(DichVu dv) {
        try {
            em.getTransaction().begin();
            em.merge(dv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(DichVu dv) {
        try {
            em.getTransaction().begin();
            em.persist(dv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(DichVu dv) {
        try {
            em.getTransaction().begin();
            em.remove(dv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
