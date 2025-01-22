package DAO;

import jakarta.persistence.EntityManager;
import util.EntityManagerUtil;

public class DieuKienApDungDAO {
   private EntityManager em =null;
    public DieuKienApDungDAO() {
         em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
}
