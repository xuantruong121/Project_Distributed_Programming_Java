package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class DichVuDAO {
    private EntityManager em =null;
    public DichVuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }

}
