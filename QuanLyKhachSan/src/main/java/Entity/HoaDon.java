package Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
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
    @OneToMany(mappedBy = "hoaDon")
//    @JoinColumn(name = "maChiTietDonDatPhong",nullable = false)
    private Set<ChiTietDonDatPhong> chiTietDonDatPhong;
    private LocalDateTime ngayLap;
    private double tongTien;
    private String ghiChu;
    @OneToMany(mappedBy = "hoaDon")
//    @JoinColumn(name = "maChiTietDichVu",unique = true)
    private Set<ChiTietDichVu> chiTietDichVu;

}
