package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class HoaDonDAO {
    private EntityManager em =null;
    public HoaDonDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
