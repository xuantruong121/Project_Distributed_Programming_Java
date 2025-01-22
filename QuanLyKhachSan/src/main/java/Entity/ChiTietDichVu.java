package Entity;

import util.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Getter
@Setter
public class ChiTietDichVu {
    @Id
    @Column(columnDefinition = "nvarchar(17)")
    @Pattern(regexp = "^\\d{6}-\\d{2}-\\d{6}$") // DDMMYY-XX-KKKKKK
    private String maChiTietDichVu;
    @ManyToOne
    @JoinColumn(name = "maDichVu")
    @NotNull
    private DichVu dichVu;
    @NotNull
    private LocalDateTime ngaySuDung;
    @Positive
    private double soLuong;
    @NotNull
    private boolean trangThai;
    @ManyToOne
    @JoinColumn(name = "maHoaDon")
    private HoaDon hoaDon;

    @PrePersist
    public void prePersist(){
        if(this.maChiTietDichVu == null){
            this.maChiTietDichVu = generateMaChiTietDichVu();
        }
    }
    public String generateMaChiTietDichVu(){
        String pattern = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy"))+"-"+this.dichVu.getMaDichVu().substring(2,4)+"-";
        String getNumbersOfMaChiTietDichVu = "SELECT COUNT(c) FROM ChiTietDichVu c WHERE c.maChiTietDichVu like '" + pattern + "%'";
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        long count = (long)em.createQuery(getNumbersOfMaChiTietDichVu).getSingleResult();
        em.close();
        return pattern + String.format("%06d",count + 1);
    }
}
