package DAO;

import Entity.VatTu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class VatTuDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public boolean update(VatTu vt) {
        try {
            em.getTransaction().begin();
            em.merge(vt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(VatTu vt) {
        try {
            em.getTransaction().begin();
            em.persist(vt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(VatTu vt) {
        try {
            em.getTransaction().begin();
            em.remove(vt);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public VatTu getVatTu(String ma) {
        VatTu vt = null;
        try {
            vt = em.find(VatTu.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vt;
    }
    public List<VatTu> getDanhSachVatTu() {
        List<VatTu> list = null;
        try {
            list = em.createQuery("SELECT vt FROM VatTu vt", VatTu.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
