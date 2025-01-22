package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class VatTuTrongKhoDAO {
    private EntityManager em =null;
    public VatTuTrongKhoDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}

