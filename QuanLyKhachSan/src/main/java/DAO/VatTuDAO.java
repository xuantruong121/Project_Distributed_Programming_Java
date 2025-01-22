package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class VatTuDAO {
    private EntityManager em =null;
    public VatTuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
