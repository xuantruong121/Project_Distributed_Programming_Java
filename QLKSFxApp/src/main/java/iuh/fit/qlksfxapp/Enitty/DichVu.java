package iuh.fit.qlksfxapp.Enitty;

import iuh.fit.qlksfxapp.DAO.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author Admin
 */
@Entity
@Getter
@Setter
public class DichVu {
    @Id
    @Column(columnDefinition = "nvarchar(4)")
    @Pattern(regexp = "^DV\\d{2}$",message = "Mã dịch vụ không hợp lệ (DVXX)")
    private String maDichVu;
    @NotBlank(message = "Tên dịch vụ không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String tenDichVu;
    @NotBlank(message = "Đơn vị tính không được để trống")
    @Column(columnDefinition = "nvarchar(25)")
    private String donViTinh;
    @PositiveOrZero(message = "Giá dịch vụ không được âm")
    private double giaDichVu;
    private String hinhAnh;
    private String moTa;
    @NotNull(message = "Trạng thái không được để trống")
    private boolean trangThai;
    @ManyToOne
    @JoinColumn(name="maLoaiDichVu")
    @NotNull
    private LoaiDichVu loaiDichVu;
    @PrePersist
    public void prePersist(){
        if(this.maDichVu == null){
            this.maDichVu = generateMaDichVu();
        }
    }
    public String generateMaDichVu(){
        String query = "SELECT COUNT(v) FROM DichVu v";
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        long count = (long)em.createQuery(query).getSingleResult();
        em.close();
        return "DV" + String.format("%02d",count + 1);
    }
}
