package DAO;

import Entity.TaiLieuChungCu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class TaiLieuChungCuDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<TaiLieuChungCu> getDanhSachTaiLieuChungCu() {
        List<TaiLieuChungCu> list = null;
        try {
            list = em.createQuery("SELECT tlcc FROM TaiLieuChungCu tlcc", TaiLieuChungCu.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean add(TaiLieuChungCu tlcc) {
        try {
            em.getTransaction().begin();
            em.persist(tlcc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(TaiLieuChungCu tlcc) {
        try {
            em.getTransaction().begin();
            em.remove(tlcc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public TaiLieuChungCu getTaiLieuChungCu(String ma) {
        TaiLieuChungCu tlcc = null;
        try {
            tlcc = em.find(TaiLieuChungCu.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return tlcc;
    }
    public boolean update(TaiLieuChungCu tlcc) {
        try {
            em.getTransaction().begin();
            em.merge(tlcc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

}
