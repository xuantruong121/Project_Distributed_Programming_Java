package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class LoaiNhanVienDAO {
    private EntityManager em =null;
    public LoaiNhanVienDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
