package DAO;

import Entity.ChiTietDonBaoCao;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChiTietDonBaoCaoDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<ChiTietDonBaoCao> getDanhSachChiTietDonBaoCao() {
        List<ChiTietDonBaoCao> list = null;
        try {
            list = em.createQuery("SELECT ctdbc FROM ChiTietDonBaoCao ctdbc", ChiTietDonBaoCao.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public ChiTietDonBaoCao getChiTietDonBaoCao(String ma) {
        ChiTietDonBaoCao ctdbc = null;
        try {
            ctdbc = em.find(ChiTietDonBaoCao.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ctdbc;
    }
    public boolean update(ChiTietDonBaoCao ctdbc) {
        try {
            em.getTransaction().begin();
            em.merge(ctdbc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(ChiTietDonBaoCao ctdbc) {
        try {
            em.getTransaction().begin();
            em.persist(ctdbc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(ChiTietDonBaoCao ctdbc) {
        try {
            em.getTransaction().begin();
            em.remove(ctdbc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
