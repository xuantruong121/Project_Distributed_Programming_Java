package iuh.fit.qlksfxapp.Entity;

import iuh.fit.qlksfxapp.DAO.Impl.EntityManagerUtilImpl;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class LoaiDichVu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "nvarchar(5)")
    @Pattern(regexp = "^LDV\\d{2}$",message = "Mã loại dịch vụ không hợp lệ (LDVXX)")
    private String maLoaiDichVu;
    @NotBlank(message = "Tên loại dịch vụ không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String tenLoaiDichVu;
    private String moTa;
    @PrePersist
    public void prePersist(){
        if(this.maLoaiDichVu == null){
            this.maLoaiDichVu = generateMaLoaiDichVu();
        }
    }
    public String generateMaLoaiDichVu(){
        String query = "SELECT COUNT(l) FROM LoaiDichVu l";
        long count = (long) EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return "LDV" + String.format("%02d",count+1);
    }
}

