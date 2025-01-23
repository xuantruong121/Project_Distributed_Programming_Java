package DAO;

import jakarta.persistence.EntityManager;

public class LichSuVatTuTrongKhoDAO {
    private EntityManager em =null;
    public LichSuVatTuTrongKhoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
