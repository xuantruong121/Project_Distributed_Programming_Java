package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class VatTuTrongKhoDAO {
    private EntityManager em =null;
    public VatTuTrongKhoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}

