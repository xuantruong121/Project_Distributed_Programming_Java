package DAO;

import Entity.DonBaoCao;
import jakarta.persistence.EntityManager;

import java.util.List;

public class DonBaoCaoDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<DonBaoCao> getDanhSachDonBaoCao() {
        List<DonBaoCao> list = null;
        try {
            list = em.createQuery("SELECT dbc FROM DonBaoCao dbc", DonBaoCao.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public DonBaoCao getDonBaoCao(String ma) {
        DonBaoCao dbc = null;
        try {
            dbc = em.find(DonBaoCao.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dbc;
    }
    public boolean update(DonBaoCao dbc) {
        try {
            em.getTransaction().begin();
            em.merge(dbc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(DonBaoCao dbc) {
        try {
            em.getTransaction().begin();
            em.persist(dbc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(DonBaoCao dbc) {
        try {
            em.getTransaction().begin();
            em.remove(dbc);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
