package iuh.fit.qlksfxapp.DAO;

import jakarta.persistence.EntityManager;

public class TaiLieuChungCuDAO {
    private EntityManager em =null;
    public TaiLieuChungCuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
