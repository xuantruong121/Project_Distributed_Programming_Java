package iuh.fit.qlksfxapp.Enitty;

import iuh.fit.qlksfxapp.DAO.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Entity
@Getter
@Setter
public class HoaDon {
    @Id
    @Column(columnDefinition = "nvarchar(15)")
    @Pattern(regexp = "^\\d{6}-(DV|DP)-\\d{5}$",message = "Mã hóa đơn phải có dạng DDMMYY-TT-ZZZZZ ")
    private String maHoaDon;

    @ManyToOne
    @JoinColumn(name = "maKhachHang",nullable = false)
    private KhachHang khachHang;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinColumn(name = "maChiTietDonDatPhong",nullable = false)
    private Set<ChiTietDonDatPhong> chiTietDonDatPhong;

    private LocalDateTime ngayTao;
    private double tongTien;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
//    @JoinColumn(name = "maChiTietDichVu",unique = true)
    private Set<ChiTietDichVu> chiTietDichVu;

    private String ghiChu;

    @PrePersist
    public void prePersist() {
        // Kiểm tra mã hóa đơn, nếu chưa có thì tự động tạo
        if (this.maHoaDon == null || this.maHoaDon.isEmpty()) {
            String loaiHoaDon = ""; // Đặt giá trị mặc định hoặc xác định loại hóa đơn trước
            if (this.chiTietDonDatPhong != null && !this.chiTietDonDatPhong.isEmpty()) {
                loaiHoaDon = "DP"; // Hóa đơn đặt phòng
            } else if (this.chiTietDichVu != null && !this.chiTietDichVu.isEmpty()) {
                loaiHoaDon = "DV"; // Hóa đơn dịch vụ
            } else {
                throw new IllegalArgumentException("Loại hóa đơn không thể xác định!");
            }
            this.maHoaDon = generateMaHoaDon(loaiHoaDon);
        }

        // Thiết lập ngày tạo nếu chưa được khởi tạo
        if (this.ngayTao == null) {
            this.ngayTao = LocalDateTime.now();
        }
    }


    public String generateMaHoaDon(String loaiHoaDon) { // Thêm tham số loại hóa đơn: "DV" hoặc "DP"
        String maHoaDon = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy")) + "-" + loaiHoaDon + "-";
        String queryStr = "SELECT COUNT(h) FROM HoaDon h WHERE h.maHoaDon LIKE :pattern";
        EntityManager em = null;
        long count = 0;
        try {
            em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
            Query query = em.createQuery(queryStr);
            query.setParameter("pattern", maHoaDon + "%");
            count = (long) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return maHoaDon + String.format("%05d", count + 1);
    }


}
