package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class PhuThuDAO {
    private EntityManager em =null;
    public PhuThuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
