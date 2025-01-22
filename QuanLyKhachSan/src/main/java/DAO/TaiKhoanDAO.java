package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class TaiKhoanDAO {
    private EntityManager em =null;
    public TaiKhoanDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
