package DAO;

import jakarta.persistence.EntityManager;

public class LoaiNhanVienDAO {
    private EntityManager em =null;
    public LoaiNhanVienDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
