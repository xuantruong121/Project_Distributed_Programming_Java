package entities;

import enums.TrangThaiDonDatPhong;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "DonDatPhong")
@Setter
@Getter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class DonDatPhong {

    @Id
    @EqualsAndHashCode.Include
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String maDonDatPhong;

    @ManyToOne
    @JoinColumn(name = "maNhanVien", nullable = false)
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "maKhachHang", nullable = false)
    private KhachHang khachHang;

    @Column(columnDefinition = "VARCHAR(45)")
    private String tenDoan;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime ngayDat;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime ngayNhan;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime ngayTra;

    @Column(columnDefinition = "INT", nullable = false)
    private int soNguoiLon;

    @Column(columnDefinition = "INT", nullable = false)
    private int soTreEm;

    @Column(columnDefinition = "DOUBLE", nullable = false)
    private double tienDatCoc;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private TrangThaiDonDatPhong trangThai;

    @Column(columnDefinition = "VARCHAR(255)")
    private String ghiChu;

    @ToString.Exclude
    @OneToMany(mappedBy = "donDatPhong", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ChiTietDonDatPhong> chiTietDonDatPhong;
}
