package Entity;

import DAO.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class VatTuTrongKho {
    @Id
    @Column(columnDefinition = "nvarchar(6)")
    @Pattern(regexp = "^K\\d{1}-\\d{3}$",message = "Mã vật tư trong kho không hợp lệ (KX-ZZZ)")
    private String maVatTuTrongKho;
    @ManyToOne
    @JoinColumn(name="maVatTu")
    @NotNull
    private VatTu vatTu;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="maKho")
    @NotNull
    private Kho kho;
    @NotBlank(message = "vị trí không được để trống")
    private String viTri;
    @PositiveOrZero
    private double soLuong;
    @PositiveOrZero
    private double soLuongToiThieu;
    @PrePersist
    public void prePersist(){
        if(this.maVatTuTrongKho==null)
        {
            this.maVatTuTrongKho = generateMaVatTuTrongKho();
        }

    }
    public String generateMaVatTuTrongKho(){
        String kho = this.kho.getMaKho()+"-";
        String query = "SELECT COUNT(v) FROM VatTuTrongKho v WHERE v.maVatTuTrongKho LIKE '" + kho + "%'";
        long count = (long) EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return kho + String.format("%03d", count + 1);
    }
}
