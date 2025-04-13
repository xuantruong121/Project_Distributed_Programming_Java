package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class PhuThuDAO {
    private EntityManager em =null;
    public PhuThuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
