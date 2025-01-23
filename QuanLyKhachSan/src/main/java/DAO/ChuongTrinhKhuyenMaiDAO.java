package DAO;

import jakarta.persistence.EntityManager;

public class ChuongTrinhKhuyenMaiDAO {
    private EntityManager em =null;
    public ChuongTrinhKhuyenMaiDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
