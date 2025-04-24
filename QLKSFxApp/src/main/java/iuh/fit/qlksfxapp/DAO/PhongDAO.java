package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PhongDAO implements  CloseEntityManager{
    private EntityManager em =null;
    public PhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }
    public List<Phong> getPhongTheoViTri(String viTri){
            String query = "SELECT p FROM Phong p WHERE p.viTri = :viTri";
            return em.createQuery(query, Phong.class)
                    .setParameter("viTri", viTri)
                    .getResultList();
    }
    public List<Phong> getPhongTheoMaDonDatPhong(String maDonDatPhong){
        String query = "SELECT p FROM Phong p JOIN ChiTietDonDatPhong c ON p.maPhong = c.phong.maPhong WHERE c.donDatPhong.maDonDatPhong = :maDonDatPhong";
        return em.createQuery(query, Phong.class)
                .setParameter("maDonDatPhong", maDonDatPhong)
                .getResultList();
    }
    public  List<Phong> getListPhongTheoTrangThaiPhongDANG_DON_DEP_DANG_SUA_CHUA_KHONG_SU_DUNG(TrangThaiPhong trangThaiPhong){
        String query = "SELECT p FROM Phong p WHERE p.trangThaiPhong = :trangThaiPhong";
        return em.createQuery(query, Phong.class)
                .setParameter("trangThaiPhong", trangThaiPhong)
                .getResultList();
    }

    @Override
    public void closeEntityManager() {
        if (em != null && em.isOpen()) {
            em.close();
        }
    }
}
