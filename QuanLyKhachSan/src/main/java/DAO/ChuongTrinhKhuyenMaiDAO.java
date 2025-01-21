package DAO;

import Entity.ChuongTrinhKhuyenMai;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChuongTrinhKhuyenMaiDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<ChuongTrinhKhuyenMai> getDanhSachChuongTrinhKhuyenMai(){
        List<ChuongTrinhKhuyenMai> list = null;
        try {
            list = em.createQuery("SELECT ctkm FROM ChuongTrinhKhuyenMai ctkm", ChuongTrinhKhuyenMai.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public  ChuongTrinhKhuyenMai getChuongTrinhKhuyenMai(String maChuongTrinhKhuyenMai) {
        try {
            return em.find(ChuongTrinhKhuyenMai.class, maChuongTrinhKhuyenMai);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public boolean add(ChuongTrinhKhuyenMai ctkm){
        try {
            em.getTransaction().begin();
            em.persist(ctkm);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean update(ChuongTrinhKhuyenMai ctkm) {
        try {
            em.getTransaction().begin();
            em.merge(ctkm);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(ChuongTrinhKhuyenMai ctkm) {
        try {
            em.getTransaction().begin();
            em.remove(ctkm);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
