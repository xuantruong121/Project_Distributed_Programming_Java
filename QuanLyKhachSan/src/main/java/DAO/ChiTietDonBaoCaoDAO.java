package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class ChiTietDonBaoCaoDAO {
    private EntityManager em =null;
    public ChiTietDonBaoCaoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
