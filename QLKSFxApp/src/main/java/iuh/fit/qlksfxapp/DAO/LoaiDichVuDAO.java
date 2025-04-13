package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class LoaiDichVuDAO {
    private EntityManager em =null;
    public LoaiDichVuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
