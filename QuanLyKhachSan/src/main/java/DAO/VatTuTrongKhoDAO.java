package DAO;

import jakarta.persistence.EntityManager;

import java.util.List;

public class VatTuTrongKhoDAO {
    private EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    public boolean update(Entity.VatTuTrongKho vttk) {
        try {
            em.getTransaction().begin();
            em.merge(vttk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean add(Entity.VatTuTrongKho vttk) {
        try {
            em.getTransaction().begin();
            em.persist(vttk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public boolean delete(Entity.VatTuTrongKho vttk) {
        try {
            em.getTransaction().begin();
            em.remove(vttk);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
    }
    public Entity.VatTuTrongKho getVatTuTrongKho(String ma) {
        Entity.VatTuTrongKho vttk = null;
        try {
            vttk = em.find(Entity.VatTuTrongKho.class, ma);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vttk;
    }
    public List<Entity.VatTuTrongKho> getDanhSachVatTuTrongKho() {
        List<Entity.VatTuTrongKho> list = null;
        try {
            list = em.createQuery("SELECT vttk FROM VatTuTrongKho vttk", Entity.VatTuTrongKho.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}

