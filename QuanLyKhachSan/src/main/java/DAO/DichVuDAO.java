package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class DichVuDAO {
    private EntityManager em =null;
    public DichVuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }

}
