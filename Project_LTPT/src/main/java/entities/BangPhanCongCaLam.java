package entities;

import enums.TrangThaiBangPhanCongCaLam;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "BangPhanCongCaLam")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class BangPhanCongCaLam {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "MaPhanCong", nullable = false, columnDefinition = "varchar(50)")
    private String maPhanCong;

    @ManyToOne
    @JoinColumn(name = "MaNhanVien", columnDefinition = "varchar(50)", nullable = false)
    private NhanVien nhanVien;

    @ManyToOne
    @JoinColumn(name = "MaCaLam", columnDefinition = "varchar(50)", nullable = false)
    private CaLamViec caLamViec;

    @Column(name = "NgayLamViec", columnDefinition = "DATE", nullable = false)
    private LocalDate ngayLamViec;

    @Enumerated(EnumType.STRING)
    @Column(name = "TrangThai", columnDefinition = "VARCHAR(20)", nullable = false)
    private TrangThaiBangPhanCongCaLam trangThai;
}
