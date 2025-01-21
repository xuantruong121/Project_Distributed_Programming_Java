package DAO;

import Entity.LichSuVatTuTrongKho;
import jakarta.persistence.EntityManager;

import java.util.List;

public class LichSuVatTuTrongKhoDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<LichSuVatTuTrongKho> getDanhSachLichSuVatTuTrongKho() {
        List<LichSuVatTuTrongKho> list = null;
        try {
            list = em.createQuery("SELECT lsvttk FROM LichSuVatTuTrongKho lsvttk", LichSuVatTuTrongKho.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public LichSuVatTuTrongKho getLichSuVatTuTrongKho(String ma) {
        LichSuVatTuTrongKho lsvttk = null;
        try {
            lsvttk = em.find(LichSuVatTuTrongKho.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lsvttk;
    }
    public boolean update(LichSuVatTuTrongKho lsvttk) {
        try {
            em.getTransaction().begin();
            em.merge(lsvttk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(LichSuVatTuTrongKho lsvttk) {
        try {
            em.getTransaction().begin();
            em.persist(lsvttk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(LichSuVatTuTrongKho lsvttk) {
        try {
            em.getTransaction().begin();
            em.remove(lsvttk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
