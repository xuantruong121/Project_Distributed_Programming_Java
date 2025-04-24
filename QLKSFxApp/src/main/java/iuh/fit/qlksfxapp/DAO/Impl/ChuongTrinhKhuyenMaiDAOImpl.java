package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManager;

public class ChuongTrinhKhuyenMaiDAOImpl {
    private EntityManager em =null;
    public ChuongTrinhKhuyenMaiDAOImpl() {
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }
}
