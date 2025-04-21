package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManager;

public class LichSuVatTuTrongKhoDAOImpl {
    private EntityManager em =null;
    public LichSuVatTuTrongKhoDAOImpl() {
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }
}
