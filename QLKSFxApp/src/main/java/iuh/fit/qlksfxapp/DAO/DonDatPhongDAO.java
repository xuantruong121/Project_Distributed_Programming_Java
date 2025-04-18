package iuh.fit.qlksfxapp.DAO;

import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import jakarta.persistence.EntityManager;

import java.time.LocalDateTime;
import java.util.List;

public class DonDatPhongDAO {
    private EntityManager em =null;
    public DonDatPhongDAO() {
        em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
    }

    public DonDatPhong getDonDatPhongNowByIdPhong(String idPhong){
        LocalDateTime now = LocalDateTime.now();
        String query = "SELECT d FROM DonDatPhong d JOIN ChiTietDonDatPhong c ON d.maDonDatPhong = c.donDatPhong.maDonDatPhong WHERE c.phong.maPhong = :idPhong AND d.ngayNhan <= :now AND d.ngayTra >= :now AND d.trangThai = :trangThai";
        List<DonDatPhong> donDatPhongs = em.createQuery(query, DonDatPhong.class)
                .setParameter("idPhong", idPhong)
                .setParameter("now", now)
                .setParameter("trangThai", TrangThaiDonDatPhong.DA_XAC_NHAN)
                .getResultList();
        if(!donDatPhongs.isEmpty()){
            return donDatPhongs.getFirst();
        }
        return null;
    }

public static void main(String[] args) {
    DonDatPhongDAO donDatPhongDAO = new DonDatPhongDAO();
    GeneralDAO generalDAO = new GeneralDAO();
    List<Phong> phongs = generalDAO.findAll(Phong.class);
    for (Phong phong : phongs) {
        System.out.println("Phong: " + phong.getMaPhong());
        DonDatPhong donDatPhong = donDatPhongDAO.getDonDatPhongNowByIdPhong(phong.getMaPhong());
        if (donDatPhong != null) {
            System.out.println("Don Dat Phong: " + donDatPhong.getMaDonDatPhong());
        } else {
            System.out.println("Khong co don dat phong nao");
        }
    }
}
}
