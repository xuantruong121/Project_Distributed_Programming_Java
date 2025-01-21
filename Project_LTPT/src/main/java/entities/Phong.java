package entities;

import enums.TrangThaiPhong;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "Phong")
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Phong {

    @Id
    @EqualsAndHashCode.Include
    @Column(name = "MaPhong", columnDefinition = "varchar(50)", nullable = false)
    private String maPhong;

    @Column(name = "ViTri", columnDefinition = "varchar(50)", nullable = false)
    private String viTri;

    @Column(name = "HinhAnh", columnDefinition = "varchar(100)", nullable = false)
    private String hinhAnh;

    @Column(name = "MoTa", columnDefinition = "varchar(255)", nullable = false)
    private String moTa;

    @Enumerated(EnumType.STRING)
    @Column(name = "TrangThai", columnDefinition = "varchar(20)", nullable = false)
    private TrangThaiPhong trangThai;
}
