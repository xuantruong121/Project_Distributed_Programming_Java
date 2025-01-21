package Entity;

import DAO.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
public class LichSuVatTuTrongKho {
    @Id
    @Column(columnDefinition = "nvarchar(12)")
    @Pattern(regexp = "^K\\d{1}-\\d{3}-\\d{5}$",message = "Mã lịch sử vật tư trong kho không hợp lệ (KX-ZZZ-YYYYY)")
    private String maLichSuVatTuTrongKho;
    @ManyToOne
    @JoinColumn(name = "maVatTuTrongKho")
    @NotNull
    private VatTuTrongKho vatTuTrongKho;
    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    @NotNull
    private NhanVien nhanVien;
    @NotNull
    private double soLuongThayDoi;
    @NotNull
    private LocalDateTime ngayThayDoi;
    @PrePersist
    public void prePersist(){
        if(this.maLichSuVatTuTrongKho == null){
            this.maLichSuVatTuTrongKho = generateMaLichSuVatTuTrongKho();
        }
    }
    public String generateMaLichSuVatTuTrongKho(){
        String pattern = this.vatTuTrongKho.getMaVatTuTrongKho()+"-";
        String query = "SELECT COUNT(l) FROM LichSuVatTuTrongKho l WHERE l.maLichSuVatTuTrongKho LIKE '" + pattern + "%'";
        long count = (long) EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return pattern + String.format("%05d",count + 1);
    }
}
