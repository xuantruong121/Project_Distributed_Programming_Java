package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class ChiTietPhongDAO {
    private EntityManager em =null;
    public ChiTietPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
