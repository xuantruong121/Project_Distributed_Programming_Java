package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class ChuongTrinhKhuyenMaiDAO {
    private EntityManager em =null;
    public ChuongTrinhKhuyenMaiDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
