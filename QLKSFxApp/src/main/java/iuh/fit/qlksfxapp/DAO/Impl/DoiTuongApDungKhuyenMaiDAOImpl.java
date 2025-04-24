package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManager;

public class DoiTuongApDungKhuyenMaiDAOImpl {
    private EntityManager em =null;
    public DoiTuongApDungKhuyenMaiDAOImpl() {
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }
}
