package iuh.fit.qlksfxapp.Entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class TaiKhoan implements Serializable {
    private static final long serialVersionUID = 1L;

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
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        TaiKhoan taiKhoan = (TaiKhoan) o;
        
        return nhanVien != null ? nhanVien.equals(taiKhoan.nhanVien) : taiKhoan.nhanVien == null;
    }
    
    @Override
    public int hashCode() {
        return nhanVien != null ? nhanVien.hashCode() : 0;
    }
}

