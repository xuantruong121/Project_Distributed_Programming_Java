package entities;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "TaiKhoan")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TaiKhoan {
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "TenDangNhap", nullable = false)
    private String tenDangNhap;

    @Column(name = "MatKhau", nullable = false)
    private String matKhau;

    @OneToOne
    @JoinColumn(name = "taiKhoan", columnDefinition = "varchar(50)", nullable = false, unique = true)
    private NhanVien nhanVien;
}
