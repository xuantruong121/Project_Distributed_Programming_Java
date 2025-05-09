package iuh.fit.qlksfxapp.Entity;

import iuh.fit.qlksfxapp.DAO.Impl.EntityManagerUtilImpl;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter
@Setter
public class PhuThu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "nvarchar(4)")
    @Pattern(regexp = "^PT\\d{2}$",message = "ma phu thu không hợp lệ (PTXX)")
    private String maPhuThu;
    @NotBlank(message = "ten phu thu không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String tenPhuThu;
    @PositiveOrZero(message = "giá phụ thu không được âm")
    private double giaPhuThu;
    private String hinhAnh;
    private String moTa;
    @ManyToOne
    @JoinColumn(name="maLoaiPhuThu")
    @NotNull(message = "loai phu thu không được để trống")
    private LoaiPhuThu loaiPhuThu;
    @PrePersist
    public void prePersist(){
        if(this.maPhuThu==null)
        {
            this.maPhuThu = generateMaPhuThu();
        }

    }
    public String generateMaPhuThu(){
        String query = "SELECT COUNT(p) FROM PhuThu p";
        long count = (long) EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return "PT" + String.format("%02d", count + 1);
    }
}

