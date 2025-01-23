package DAO;

import jakarta.persistence.EntityManager;

public class CaLamViecDAO {
    private EntityManager em =null;
    public CaLamViecDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }

}
