package iuh.fit.qlksfxapp.Enitty;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TaiKhoan {
    @Column(nullable = false)
//    @Pattern(regexp = "^(?=(.*[a-z]))(?=(.*[A-Z]))(?=(.*\\d))(?=(.*[!@#$%^&*(),.?\":{}|<>])).{6,}$",message =
//    "matKhau không hợp lệ (Độ dài hớn 6 kí tự và có chứa kí tự in hoa, kí tự thường, số và kí tự đặc biệt)")
    private String matKhau;
    @Id
    @OneToOne
    @JoinColumn(name = "maNhanVien", unique = true)
    @NotNull
    private NhanVien nhanVien;
    public static boolean checkPassW(String ps){
        return ps.matches("^(?=(.*[a-z]))(?=(.*[A-Z]))(?=(.*\\d))(?=(.*[!@#$%^&*(),.?\":{}|<>])).{6,}$");
    }
}
