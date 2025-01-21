package entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "KhachHang")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KhachHang {

    @Id
    @Column(columnDefinition = "varchar(40)", name = "MaKhachHang", nullable = false)
    @EqualsAndHashCode.Include
    private String maKhachHang;

    @Column(columnDefinition = "varchar(50)", name = "TenKhachHang", nullable = false)
    private String tenKhachHang;

    @Column(columnDefinition = "varchar(10)", name = "SoDienThoai", nullable = false, unique = true)
    private String soDienThoai;

    @Column(columnDefinition = "varchar(12)", name = "CanCuocCongDan", nullable = false, unique = true)
    private String canCuocCongDan;

    @Column(columnDefinition = "varchar(100)", name = "Email", nullable = false)
    private String email;

    @Column(columnDefinition = "varchar(45)", name = "NgaySinh")
    private LocalDate ngaySinh;

    @Column(columnDefinition = "bit", name = "GioiTinh")
    private boolean gioiTinh;

    @Column(columnDefinition = "int", name = "DiemTichLuy")
    private int diemTichLuy;

    @Column(columnDefinition = "varchar(20)", name = "QuocTich")
    private String quocTich;

    @ToString.Exclude
    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<DonDatPhong> donDatPhongs;

    @ToString.Exclude
    @OneToMany(mappedBy = "khachHang", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<KhachHangTrongPhong> khachHangTrongPhongList;
}
