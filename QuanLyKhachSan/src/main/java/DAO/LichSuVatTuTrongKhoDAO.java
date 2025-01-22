package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class LichSuVatTuTrongKhoDAO {
    private EntityManager em =null;
    public LichSuVatTuTrongKhoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
