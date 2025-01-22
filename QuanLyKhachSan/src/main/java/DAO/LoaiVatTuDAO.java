package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class LoaiVatTuDAO {
    private EntityManager em =null;
    public LoaiVatTuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
