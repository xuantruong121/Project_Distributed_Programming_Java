package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class DonDatPhongDAO {
    private EntityManager em =null;
    public DonDatPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
