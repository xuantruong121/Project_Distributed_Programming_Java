package entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "CaLamViec")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CaLamViec {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "MaCaLam", nullable = false, columnDefinition = "varchar(50)")
    private String maCaLam;

    @Column(name = "ThoiGianBatDau", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime thoiGianBatDau;

    @Column(name = "ThoiGianKetThuc", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime thoiGianKetThuc;

    @Column(name = "TenCaLam", nullable = false, columnDefinition = "varchar(50)")
    private String tenCaLam;

    @Column(name = "MoTa", columnDefinition = "varchar(255)")
    private String moTa;

    @ToString.Exclude
    @OneToMany(mappedBy = "caLamViec", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<BangPhanCongCaLam> bangPhanCongCaLamList;
}
