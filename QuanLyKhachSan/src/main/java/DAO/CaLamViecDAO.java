package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class CaLamViecDAO {
    private EntityManager em =null;
    public CaLamViecDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }

}
