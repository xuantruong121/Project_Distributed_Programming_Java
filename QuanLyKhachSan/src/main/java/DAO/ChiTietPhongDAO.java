package DAO;

import jakarta.persistence.EntityManager;

public class ChiTietPhongDAO {
    private EntityManager em =null;
    public ChiTietPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
