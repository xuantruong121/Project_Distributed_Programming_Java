package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class KhoDAO {
    private EntityManager em =null;
    public KhoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
