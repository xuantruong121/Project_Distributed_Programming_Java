package Entity;

import util.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import Enum.TrangThaiChiTietDonDatPhong;

@Entity
@Getter
@Setter
public class ChiTietDonDatPhong {
    @Id
    @Column(columnDefinition = "nvarchar(13)")
    @Pattern(regexp = "^\\d{9}-\\d{3}$") //DDMMYYAAA-YYY
    private String maChiTietDonDatPhong;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDonDatPhong")
    @NotNull
    private DonDatPhong donDatPhong;
    @ManyToOne
    @JoinColumn(name = "maPhong")
    @NotNull
    private Phong phong;
    @NotNull
    private LocalDateTime ngayNhan;
    @NotNull
    private LocalDateTime ngayTra;
    @Enumerated(EnumType.STRING)
    private TrangThaiChiTietDonDatPhong trangThaiChiTietDonDatPhong;
    @OneToMany()
    @JoinColumn(name = "maChiTietDonDatPhong")
    private Set<ChiTietDichVu> chiTietDichVu;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "chiTietPhuThuTrongPhong",
            joinColumns = @JoinColumn(name = "maChiTietDonDatPhong",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "maPhuThu",nullable = false)
    )
    private Set<PhuThu> phuThu;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "khachHangTrongPhong",
            joinColumns = @JoinColumn(name = "maChiTietDonDatPhong",nullable = false),
            inverseJoinColumns = @JoinColumn(name = "maKhachHang",nullable = false)
    )
    private Set<KhachHang> khachHang;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maHoaDon")
    private HoaDon hoaDon;
    @PrePersist
    public void prePersist(){
        if(this.maChiTietDonDatPhong == null){
            this.maChiTietDonDatPhong = generateMaChiTietDonDatPhong();
        }
    }
    public String generateMaChiTietDonDatPhong(){
        String pattern = this.donDatPhong.getMaDonDatPhong()+"-";
        String getNumbersOfMaChiTietDonDatPhong = "SELECT COUNT(c) FROM ChiTietDonDatPhong c where c.maChiTietDonDatPhong like '" + pattern + "%'";
        EntityManager em =EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        long numbersOfMaChiTietDonDatPhong = (long)em.createQuery(getNumbersOfMaChiTietDonDatPhong).getSingleResult();
        em.close();
        return pattern + String.format("%03d",numbersOfMaChiTietDonDatPhong + 1);
    }
}
