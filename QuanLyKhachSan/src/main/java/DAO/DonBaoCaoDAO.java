package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class DonBaoCaoDAO {
    private EntityManager em =null;
    public DonBaoCaoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
