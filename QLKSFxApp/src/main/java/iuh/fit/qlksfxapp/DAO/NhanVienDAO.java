package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class NhanVienDAO {
    private EntityManager em =null;
    public NhanVienDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
