package DAO;

import jakarta.persistence.EntityManager;

public class DieuKienApDungDAO {
   private EntityManager em =null;
    public DieuKienApDungDAO() {
         em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
