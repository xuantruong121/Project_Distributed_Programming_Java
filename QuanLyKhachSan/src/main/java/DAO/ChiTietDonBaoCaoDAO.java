package DAO;

import jakarta.persistence.EntityManager;

public class ChiTietDonBaoCaoDAO {
    private EntityManager em =null;
    public ChiTietDonBaoCaoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
