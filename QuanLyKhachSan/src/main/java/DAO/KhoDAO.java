package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class KhoDAO {
    private EntityManager em =null;
    public KhoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
