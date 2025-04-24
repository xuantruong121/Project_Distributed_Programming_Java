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
public class VatTu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "nvarchar(5)")
    @Pattern(regexp = "^VT\\d{3}$",message = "Mã vật tư không hợp lệ (VTXXX)")
    private String maVatTu;
    @NotBlank(message = "Tên vật tư không được để trống")
    @Column(columnDefinition = "nvarchar(50)")
    private String tenVatTu;
    @NotBlank(message = "Đơn vị tính không được để trống")
    @Column(columnDefinition = "nvarchar(30)")
    private String donViTinh;
    @PositiveOrZero
    private double gia;
    private String hinhAnh;
    @ManyToOne
    @JoinColumn(name="maLoaiVatTu")
    @NotNull
    private LoaiVatTu loaiVatTu;
    @PrePersist
    public void prePersist(){
        if(this.maVatTu==null)
        {
            this.maVatTu = generateMaVatTu();
        }

    }
    public String generateMaVatTu(){
        String query = "SELECT COUNT(v) FROM VatTu v";
        long count = (long) EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return "VT" + String.format("%03d", count + 1);
    }
}

