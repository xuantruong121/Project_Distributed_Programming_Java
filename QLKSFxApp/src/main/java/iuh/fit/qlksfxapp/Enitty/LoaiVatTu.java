package iuh.fit.qlksfxapp.Enitty;

import iuh.fit.qlksfxapp.DAO.EntityManagerUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LoaiVatTu {
    @Id
    @Column(columnDefinition = "nvarchar(5)")
    @Pattern(regexp = "^LVT\\d{2}$",message = "Mã loại vật tư không hợp lệ (LVTXX)")
    private String maLoaiVatTu;
    @NotBlank(message = "Tên loại vật tư không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String tenLoaiVatTu;
    private String moTa;
    @PrePersist
    public void prePersist(){
        if(this.maLoaiVatTu == null){
            this.maLoaiVatTu = generateMaLoaiVatTu();
        }
    }
    public String generateMaLoaiVatTu(){
        String query = "SELECT COUNT(l) FROM LoaiVatTu l";
        long count = (long) EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return "LVT" + String.format("%02d",count+1);
    }
}
