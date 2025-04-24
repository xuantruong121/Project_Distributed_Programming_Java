package iuh.fit.qlksfxapp.Entity;

import iuh.fit.qlksfxapp.DAO.Impl.EntityManagerUtilImpl;
import iuh.fit.qlksfxapp.Entity.Constraints.KhachHangConstraints;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
@KhachHangConstraints
@ToString
 @EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class KhachHang implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "nvarchar(12)")
    @EqualsAndHashCode.Include
    private String maKhachHang;
    @NotBlank(message = "Tên khách hàng không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String tenKhachHang;
    @NotBlank(message = "Số điện thoại không được để trống")
    @Column(columnDefinition = "nvarchar(10)")
    private String soDienThoai;
    @NotBlank(message = "Căn cước công dân không được để trống")
    @Column(columnDefinition = "nvarchar(12)", unique = true)
    private String canCuocCongDan;
    @NotBlank(message = "Email không được để trống")
    private String email;
    @NotNull
    private LocalDate ngaySinh;
    @NotNull
    private boolean gioiTinh;
    private int diemTichLuy;
    @NotBlank(message = "Quốc tịch không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String quocTich;

    @PrePersist
    public void prePersist() {
        if (this.maKhachHang == null) {
            this.maKhachHang = generateMaKhachHang();
        }
    }

    public String generateMaKhachHang() {
        String formatDate = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy"));
        String query = "SELECT COUNT(k) FROM KhachHang k where k.maKhachHang like '" + formatDate + "%'";
        long count = (long) EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager()
                .createQuery(query).getSingleResult();
        System.out.println(formatDate + String.format("%06d", count + 1));
        return formatDate + String.format("%06d", count + 1);
    }
}

