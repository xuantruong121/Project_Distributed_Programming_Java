package DAO;

import Entity.DieuKienApDung;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class DieuKienApDungDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<DieuKienApDung> getDanhSachDieuKienApDung() {
        List<DieuKienApDung> list = null;
        try {
            list = em.createQuery("SELECT dkad FROM DieuKienApDung dkad", DieuKienApDung.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public DieuKienApDung getDieuKienApDung(String ma) {
        DieuKienApDung dkad = null;
        try {
            dkad = em.find(DieuKienApDung.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dkad;
    }
    public boolean update(DieuKienApDung dkad) {
        try {
            em.getTransaction().begin();
            em.merge(dkad);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(DieuKienApDung dkad) {
        try {
            em.getTransaction().begin();
            em.persist(dkad);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(DieuKienApDung dkad) {
        try {
            em.getTransaction().begin();
            em.remove(dkad);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
