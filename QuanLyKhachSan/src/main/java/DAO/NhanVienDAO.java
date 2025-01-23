package DAO;

import jakarta.persistence.EntityManager;

<<<<<<< HEAD

=======
>>>>>>> 3876203ba275795eb5c0448a2b130c2628fcbd7e
public class NhanVienDAO {
    private EntityManager em =null;
    public NhanVienDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
