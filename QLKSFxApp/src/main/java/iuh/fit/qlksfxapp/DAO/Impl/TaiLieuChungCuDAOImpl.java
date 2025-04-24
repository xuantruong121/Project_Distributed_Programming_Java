package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManager;

public class TaiLieuChungCuDAOImpl {
    private EntityManager em =null;
    public TaiLieuChungCuDAOImpl() {
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }
}
