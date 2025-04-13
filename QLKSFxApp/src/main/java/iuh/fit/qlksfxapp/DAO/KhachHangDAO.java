package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class KhachHangDAO {
    private EntityManager em =null;
    public KhachHangDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
