package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManager;

public class ChiTietDonBaoCaoDAOImpl {
    private EntityManager em =null;
    public ChiTietDonBaoCaoDAOImpl() {
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }
}
