package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class HoaDonDAO {
    private EntityManager em =null;
    public HoaDonDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
