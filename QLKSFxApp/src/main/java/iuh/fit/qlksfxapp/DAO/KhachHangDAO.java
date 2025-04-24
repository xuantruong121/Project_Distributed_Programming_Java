package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.KhachHang;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.NoResultException;
import lombok.Getter;

import java.util.Set;

@Getter
public class KhachHangDAO implements CloseEntityManager {
    private EntityManager em ;
    public KhachHangDAO() {
        EntityManagerFactory emf = EntityManagerUtil.getEntityManagerFactory();
        em = emf.createEntityManager();
    }
    public KhachHang findKhachHangByCccd(String cccd) {
        String query = "SELECT k FROM KhachHang k WHERE k.canCuocCongDan = :cccd";
        try {
            return em.createQuery(query, KhachHang.class)
                        .setParameter("cccd", cccd)
                        .getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }
    public Set<KhachHang> findKhachHangByMaChiTietDonDatPhong(String maChiTiet) {
        String query = "SELECT  k FROM ChiTietDonDatPhong c " +
                "JOIN c.khachHang k " +
                "WHERE c.maChiTietDonDatPhong = :ma";
        return Set.copyOf(em.createQuery(query, KhachHang.class)
                .setParameter("ma", maChiTiet)
                .getResultList());
    }
    @Override
    public void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
