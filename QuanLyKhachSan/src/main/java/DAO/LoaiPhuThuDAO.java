package DAO;

import jakarta.persistence.EntityManager;

public class LoaiPhuThuDAO {
    private EntityManager em =null;
    public LoaiPhuThuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
