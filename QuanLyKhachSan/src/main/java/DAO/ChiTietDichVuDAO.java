package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class ChiTietDichVuDAO {
    private EntityManager em =null;
    public ChiTietDichVuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
