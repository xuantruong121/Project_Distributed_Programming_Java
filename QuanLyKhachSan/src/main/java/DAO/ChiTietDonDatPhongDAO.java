package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class ChiTietDonDatPhongDAO {
    private EntityManager em =null;
    public ChiTietDonDatPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
