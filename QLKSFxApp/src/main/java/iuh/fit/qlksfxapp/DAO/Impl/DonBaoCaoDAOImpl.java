package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManager;

public class DonBaoCaoDAOImpl {
    private EntityManager em =null;
    public DonBaoCaoDAOImpl() {
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }
}
