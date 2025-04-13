package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class LoaiPhongDAO {
    private EntityManager em =null;
    public LoaiPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
