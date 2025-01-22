package DAO;

import Entity.DoiTuongApDungKhuyenMai;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class DoiTuongApDungKhuyenMaiDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<DoiTuongApDungKhuyenMai> getDanhSachDoiTuongApDungKhuyenMai() {
        List<DoiTuongApDungKhuyenMai> list = null;
        try {
            list = em.createQuery("SELECT dta FROM DoiTuongApDungKhuyenMai dta", DoiTuongApDungKhuyenMai.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public DoiTuongApDungKhuyenMai getDoiTuongApDungKhuyenMai(String ma) {
        DoiTuongApDungKhuyenMai dta = null;
        try {
            dta = em.find(DoiTuongApDungKhuyenMai.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dta;
    }
    public boolean update(DoiTuongApDungKhuyenMai dta) {
        try {
            em.getTransaction().begin();
            em.merge(dta);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(DoiTuongApDungKhuyenMai dta) {
        try {
            em.getTransaction().begin();
            em.persist(dta);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(DoiTuongApDungKhuyenMai dta) {
        try {
            em.getTransaction().begin();
            em.remove(dta);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
