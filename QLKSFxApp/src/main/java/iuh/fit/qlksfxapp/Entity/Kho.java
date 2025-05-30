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
public class Kho implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "nvarchar(2)")
    @Pattern(regexp = "^K\\d{1}$",message = "Mã kho không hợp lệ (KX)")
    private String maKho;
    @NotBlank(message = "Tên kho không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String tenKho;
    private String viTri;
    @PrePersist
    public void prePersist(){
        if(this.maKho == null){
            this.maKho = generateMaKho();
        }
    }
    public String generateMaKho(){
        String query = "SELECT COUNT(k) FROM Kho k";
        long count = (long) EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return "K" + (count+1);
    }
}

