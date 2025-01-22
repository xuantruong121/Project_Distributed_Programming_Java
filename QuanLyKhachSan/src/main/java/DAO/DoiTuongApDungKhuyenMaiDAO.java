package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class DoiTuongApDungKhuyenMaiDAO {
    private EntityManager em =null;
    public DoiTuongApDungKhuyenMaiDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
