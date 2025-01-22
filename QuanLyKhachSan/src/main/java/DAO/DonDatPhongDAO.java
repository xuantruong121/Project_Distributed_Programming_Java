package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class DonDatPhongDAO {
    private EntityManager em =null;
    public DonDatPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
