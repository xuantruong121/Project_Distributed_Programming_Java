package Entity;

import Constraints.NhanVienConstraints;
import DAO.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@NhanVienConstraints
public class NhanVien {
    @Id
    @Column(columnDefinition = "nvarchar(10)")
    private String maNhanVien;
    @Column(columnDefinition = "nvarchar(50)")
    @NotBlank(message = "Tên nhân viên không được để trống") // not null, not empty, and not just whitespace.
    private String tenNhanVien;
    @Column(columnDefinition = "nvarchar(10)")
    @NotBlank(message = "Số điện thoại không được để trống")
    private String soDienThoai;
    @Column(columnDefinition = "nvarchar(12)")
    @NotBlank(message = "Căn cước công dân không được để trống")
    private String canCuocCongDan;
    @Email
    private String email;
    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;
    @Past
    private LocalDate ngaySinh;
    @NotNull
    private boolean gioiTinh;
    @NotBlank(message = "Trạng thái không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String trangThai;
    @ManyToOne
    @JoinColumn(name = "maLoaiNhanVien")
    private LoaiNhanVien loaiNhanVien;
    @PrePersist
    public void prePersist() {
        if(this.maNhanVien == null) {
            this.maNhanVien = generateMaNhanVien();
        }
    }
    public String generateMaNhanVien() {
        String tenLoaiNV = "RAT"; // Room attendant
        if(tenLoaiNV.equals("Nhân viên quản lý")) {
            tenLoaiNV="MNG";// Manager
        }
        else if(tenLoaiNV.equals("Nhân viên lễ tân")){
            tenLoaiNV="REC";// Receptionist
        }
        String pattern= tenLoaiNV + "-" + LocalDate.now().format(DateTimeFormatter.ofPattern("yy"));
        String query = "SELECT COUNT(n) FROM NhanVien n where n.maNhanVien like '" + pattern + "%'";
        long count = (long) EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return pattern + String.format("%04d",count + 1);
    }
}
