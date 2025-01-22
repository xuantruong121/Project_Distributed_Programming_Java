package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class BangPhanCongCaLamDAO {
    private EntityManager em =null;
    public BangPhanCongCaLamDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
