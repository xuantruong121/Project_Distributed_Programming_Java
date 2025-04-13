package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class ChiTietDichVuDAO {
    private EntityManager em =null;
    public ChiTietDichVuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
