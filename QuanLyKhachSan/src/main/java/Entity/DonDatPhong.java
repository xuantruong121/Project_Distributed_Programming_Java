package Entity;

import Constraints.DonDatPhongConstraints;
import util.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import Enum.TrangThaiDonDatPhong;

@Entity
@Getter
@Setter
@DonDatPhongConstraints
public class DonDatPhong {
    @Id
    @Column(columnDefinition = "nvarchar(9)")
    private String maDonDatPhong;

    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "maKhachHang")
    private KhachHang khachHang;

    @Column(columnDefinition = "nvarchar(50)")
    private String tenDoan;
    @NotNull
    private LocalDateTime ngayDat;
    @NotNull
    private LocalDateTime ngayNhan;
    @NotNull
    private LocalDateTime ngayTra;
    @Positive
    private int soLuongNguoiLon;
    @PositiveOrZero
    private int soLuongTreEm;
    @PositiveOrZero
    private double tienDatCoc;

    @Enumerated(EnumType.STRING)
    private TrangThaiDonDatPhong trangThai;

    private String ghiChu;

    @PrePersist
    public void prePersist(){
        if(this.maDonDatPhong == null){
            this.maDonDatPhong = generateMaDonDatPhong();
        }
    }

    public String generateMaDonDatPhong(){
        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy"));
        String query = "SELECT COUNT(d) FROM DonDatPhong d where d.maDonDatPhong like '" + formatDate + "%'";
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        long count = (long)em.createQuery(query).getSingleResult();
        em.close();
        return formatDate + String.format("%03d",count + 1);
    }
}
