package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManager;

public class KhoDAOImpl {
    private EntityManager em =null;
    public KhoDAOImpl() {
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }
}
