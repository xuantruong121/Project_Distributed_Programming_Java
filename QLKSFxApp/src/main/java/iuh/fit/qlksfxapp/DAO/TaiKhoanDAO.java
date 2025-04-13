package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class TaiKhoanDAO {
    private EntityManager em =null;
    public TaiKhoanDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
