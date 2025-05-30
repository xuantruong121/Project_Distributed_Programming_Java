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
public class LoaiNhanVien implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "nvarchar(5)")
    @Pattern(regexp = "^LNV\\d{2}$",message = "ma loai nhan vien không hợp lệ (LNVXX)")
    private String maLoaiNhanVien;
    @Column(columnDefinition = "nvarchar(50)")
    @NotBlank(message = "ten loai nhan vien không được để trống")
    private String tenLoaiNhanVien;
    private String moTa;
    @PrePersist
    public void prePersist(){
        if(this.maLoaiNhanVien == null){
            this.maLoaiNhanVien = generateMaLoaiNhanVien();
        }
    }
    public String generateMaLoaiNhanVien(){
        String query = "SELECT COUNT(l) FROM LoaiNhanVien l";
        long count = (long) EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return "LNV" + String.format("%02d",count+1);
    }
}

