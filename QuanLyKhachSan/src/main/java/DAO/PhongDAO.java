package DAO;

import Entity.Phong;
import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

import java.util.List;

public class PhongDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public boolean add(Phong p){
        try {
            em.getTransaction().begin();
            em.persist(p);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean update(Phong p) {
        try {
            em.getTransaction().begin();
            em.merge(p);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(Phong p) {
        try {
            em.getTransaction().begin();
            em.remove(p);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public  List<Phong> getDanhSachPhong(){

        List<Phong> list = null;
        try {
            list = em.createQuery("SELECT p FROM Phong p", Phong.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public  Phong getPhong(String ma) {
        Phong p = null;
        try {
            p = em.find(Phong.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return p;
    }

}
