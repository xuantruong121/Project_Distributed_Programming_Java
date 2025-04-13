package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class DoiTuongApDungKhuyenMaiDAO {
    private EntityManager em =null;
    public DoiTuongApDungKhuyenMaiDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
