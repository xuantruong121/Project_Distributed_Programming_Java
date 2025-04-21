package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManager;

public class VatTuTrongKhoDAOImpl {
    private EntityManager em =null;
    public VatTuTrongKhoDAOImpl() {
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }
}

