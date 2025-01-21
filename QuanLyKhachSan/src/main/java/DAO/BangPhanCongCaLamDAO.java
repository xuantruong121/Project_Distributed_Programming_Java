package DAO;

import Entity.BangPhanCongCaLam;
import jakarta.persistence.EntityManager;

import java.util.List;

public class BangPhanCongCaLamDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<BangPhanCongCaLam> getDanhSachBangPhanCongCaLam() {
        List<BangPhanCongCaLam> list = null;
        try {
            list = em.createQuery("SELECT bpcc FROM BangPhanCongCaLam bpcc", BangPhanCongCaLam.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public BangPhanCongCaLam getBangPhanCongCaLam(String ma) {
        BangPhanCongCaLam bpcc = null;
        try {
            bpcc = em.find(BangPhanCongCaLam.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bpcc;
    }
    public boolean update(BangPhanCongCaLam bpcc) {
        try {
            em.getTransaction().begin();
            em.merge(bpcc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(BangPhanCongCaLam bpcc) {
        try {
            em.getTransaction().begin();
            em.persist(bpcc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(BangPhanCongCaLam bpcc) {
        try {
            em.getTransaction().begin();
            em.remove(bpcc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
