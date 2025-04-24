package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class LichSuVatTuTrongKhoDAO {
    private EntityManager em =null;
    public LichSuVatTuTrongKhoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
