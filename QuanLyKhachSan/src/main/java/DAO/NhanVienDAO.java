package DAO;

import Entity.NhanVien;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class NhanVienDAO {
   private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public List<NhanVien> getDanhSachNhanVien(){
        List<NhanVien> list = null;
        try {
            list = em.createQuery("SELECT nv FROM NhanVien nv", NhanVien.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public NhanVien getNhanVien(String ma){
        NhanVien nv = null;
        try {
            nv = em.find(NhanVien.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return nv;
    }
    public boolean update(NhanVien nv) {
        try {
            em.getTransaction().begin();
            em.merge(nv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(NhanVien nv) {
        try {
            em.getTransaction().begin();
            em.persist(nv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(NhanVien nv) {
        try {
            em.getTransaction().begin();
            em.remove(nv);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
}
