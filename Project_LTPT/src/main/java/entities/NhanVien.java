package entities;

import enums.TrangThaiNhanVien;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "NhanVien")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class NhanVien {

    @Id
    @Column(name = "MaNhanVien", columnDefinition = "varchar(50)", nullable = false)
    @EqualsAndHashCode.Include
    private String maNhanVien;

    @ManyToOne
    @JoinColumn(name = "MaLoaiNhanVien", columnDefinition = "varchar(50)", nullable = false)
    private LoaiNhanVien loaiNhanVien;

    @Column(name = "TenNhanVien", columnDefinition = "nvarchar(255)", nullable = false)
    private String tenNhanVien;

    @Column(name = "SoDienThoai", columnDefinition = "bit", nullable = false)
    private String soDienThoai;

    @Column(name = "CanCuocCongDan", columnDefinition = "varchar(50)", nullable = false)
    private String canCuocCongDan;

    @Column(name = "Email", columnDefinition = "varchar(50)", nullable = false)
    private String email;

    @Column(name = "DiaChi", columnDefinition = "nvarchar(255)", nullable = false)
    private String diaChi;

    @Column(name = "NgaySinh", columnDefinition = "date", nullable = false)
    private LocalDate ngaySinh;

    @Column(name = "GioiTinh", columnDefinition = "boolean", nullable = false)
    private boolean gioiTinh;

    @Enumerated(EnumType.STRING)
    @Column(name = "TrangThai", columnDefinition = "varchar(50)", nullable = false)
    private TrangThaiNhanVien trangThai;

    @OneToOne(mappedBy = "nhanVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private TaiKhoan taiKhoan;

    @OneToMany(mappedBy = "nhanVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BangPhanCongCaLam> bangPhanCongCaLamList;
}
