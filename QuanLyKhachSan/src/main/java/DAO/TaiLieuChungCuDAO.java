package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class TaiLieuChungCuDAO {
    private EntityManager em =null;
    public TaiLieuChungCuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
