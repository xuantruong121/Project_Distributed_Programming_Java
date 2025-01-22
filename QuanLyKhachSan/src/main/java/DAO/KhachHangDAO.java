package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class KhachHangDAO {
    private EntityManager em =null;
    public KhachHangDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
