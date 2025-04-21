package iuh.fit.qlksfxapp.DAO.Impl;

import jakarta.persistence.EntityManager;

public class DieuKienApDungDAOImpl {
   private EntityManager em =null;
    public DieuKienApDungDAOImpl() {
         em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }
}
