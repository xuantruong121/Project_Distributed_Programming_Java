package entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@Table(name = "Loainhanvien")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class LoaiNhanVien {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "MaLoaiNhanVien", columnDefinition = "varchar(45)", nullable = false)
    private String maLoaiNhanVien;

    @Column(name = "TenLoaiNhanVien", columnDefinition = "varchar(45)", nullable = false, unique = true)
    private String tenLoaiNhanVien;

    @Column(columnDefinition = "varchar(50)")
    private String moTa;

    @ToString.Exclude
    @OneToMany(mappedBy = "loaiNhanVien", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<NhanVien> nhanVienList;
}
