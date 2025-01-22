package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class PhongDAO {
    private EntityManager em =null;
    public PhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
