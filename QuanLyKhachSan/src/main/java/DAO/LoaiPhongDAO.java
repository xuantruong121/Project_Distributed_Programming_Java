package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class LoaiPhongDAO {
    private EntityManager em =null;
    public LoaiPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
