package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class LoaiPhuThuDAO {
    private EntityManager em =null;
    public LoaiPhuThuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
