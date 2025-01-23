package DAO;

import jakarta.persistence.EntityManager;

public class KhoDAO {
    private EntityManager em =null;
    public KhoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
