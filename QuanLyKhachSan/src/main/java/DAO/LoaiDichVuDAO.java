package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class LoaiDichVuDAO {
    private EntityManager em =null;
    public LoaiDichVuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
