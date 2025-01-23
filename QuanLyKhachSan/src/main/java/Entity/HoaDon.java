package Entity;

import DAO.EntityManagerUtil;
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

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "maChiTietDonDatPhong",nullable = false)
    private Set<ChiTietDonDatPhong> chiTietDonDatPhong;

    private LocalDateTime ngayTao;
    private double tongTien;

    @OneToMany(mappedBy = "hoaDon", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "maChiTietDichVu",unique = true)
    private Set<ChiTietDichVu> chiTietDichVu;

    private String ghiChu;

    @PrePersist
    public void prePersist(){
        if(this.maHoaDon == null){
            this.maHoaDon = generateMaHoaDon();
        }
        if (this.ngayTao == null){
            this.ngayTao = LocalDateTime.now();
        }
    }

    public String generateMaHoaDon(){
        String maHoaDon = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy")) + "-";
        String query = "SELECT COUNT(h) FROM HoaDon h WHERE h.maHoaDon LIKE '" + maHoaDon + "%'";
        long count = (long) EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return maHoaDon + String.format("%05d",count + 1);
    }


}
