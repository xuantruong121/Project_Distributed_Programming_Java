package DAO;

import Entity.TaiKhoan;
import jakarta.persistence.EntityManager;

public class TaiKhoanDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public boolean checkLogin(String username, String password) {
        try {
            TaiKhoan tk = em.find(TaiKhoan.class, username);
            if (tk != null && tk.getMatKhau().equals(password)) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean update(TaiKhoan tk) {
        try {
            em.getTransaction().begin();
            em.merge(tk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(TaiKhoan tk) {
        try {
            em.getTransaction().begin();
            em.persist(tk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(TaiKhoan tk) {
        try {
            em.getTransaction().begin();
            em.remove(tk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }

}
