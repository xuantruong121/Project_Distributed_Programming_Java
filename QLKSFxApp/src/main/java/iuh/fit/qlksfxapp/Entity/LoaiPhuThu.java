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
public class LoaiPhuThu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "nvarchar(5)")
    @Pattern(regexp = "^LPT\\d{2}$",message = "ma loai phu thu không hợp lệ (LPTXX)")
    private String maLoaiPhuThu;
    @NotBlank(message = "ten loai phu thu không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String tenLoaiPhuThu;
    private String moTa;
    @PrePersist
    public void prePersist(){
        if(this.maLoaiPhuThu == null){
            this.maLoaiPhuThu = generateMaLoaiPhuThu();
        }
    }
    public String generateMaLoaiPhuThu(){
        String query = "SELECT COUNT(l) FROM LoaiPhuThu l";
        long count = (long) EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return "LPT" + String.format("%02d",count+1);
    }
}

