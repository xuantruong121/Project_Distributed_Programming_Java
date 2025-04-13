package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class VatTuDAO {
    private EntityManager em =null;
    public VatTuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
