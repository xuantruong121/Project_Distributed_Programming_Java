package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class BangPhanCongCaLamDAO {
    private EntityManager em =null;
    public BangPhanCongCaLamDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
