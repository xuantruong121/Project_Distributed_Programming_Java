package DAO;

import jakarta.persistence.EntityManager;

public class LoaiPhongDAO {
    private EntityManager em =null;
    public LoaiPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
