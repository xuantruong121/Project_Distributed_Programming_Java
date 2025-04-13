package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class ChiTietDonDatPhongDAO {
    private EntityManager em =null;
    public ChiTietDonDatPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
