package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.ChiTietDichVu;
import jakarta.persistence.EntityManager;

import java.util.List;

public class ChiTietDichVuDAO {
    private EntityManager em =null;
    public ChiTietDichVuDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
    public List<ChiTietDichVu> getListChiTietDichVuByMaChiTietDonDatPhong(String maChiTietDonDatPhong) {
        try {
            return  em.createQuery("SELECT c FROM ChiTietDichVu c WHERE c.chiTietDonDatPhong.maChiTietDonDatPhong = :maChiTietDonDatPhong", ChiTietDichVu.class)
                    .setParameter("maChiTietDonDatPhong", maChiTietDonDatPhong)
                    .getResultList();
        }finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }

    }
}
