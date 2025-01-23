package DAO;

import jakarta.persistence.EntityManager;

public class LoaiVatTuDAO {
    private EntityManager em =null;
    public LoaiVatTuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
