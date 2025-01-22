package DAO;

import jakarta.persistence.EntityManager;

public class PhongDAO {
    private EntityManager em =null;
    public PhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
