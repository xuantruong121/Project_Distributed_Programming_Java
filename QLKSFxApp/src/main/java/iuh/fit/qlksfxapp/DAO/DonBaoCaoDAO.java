package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class DonBaoCaoDAO {
    private EntityManager em =null;
    public DonBaoCaoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
