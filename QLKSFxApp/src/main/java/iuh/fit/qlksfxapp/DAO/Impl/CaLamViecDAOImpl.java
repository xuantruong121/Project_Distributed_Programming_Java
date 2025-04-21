package iuh.fit.qlksfxapp.DAO.Impl;

import iuh.fit.qlksfxapp.DAO.CaLamViecDAO;
import iuh.fit.qlksfxapp.Entity.CaLamViec;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CaLamViecDAOImpl extends GeneralDAOImpl implements CaLamViecDAO {
    private EntityManager em = null;

    public CaLamViecDAOImpl() {
        super();
        em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
    }

    @Override
    public List<CaLamViec> getAllCaLamViec() {
        return findAll(CaLamViec.class);
    }

    @Override
    public CaLamViec findByMaCaLam(String maCaLam) {
        return findOb(CaLamViec.class, maCaLam);
    }
}
