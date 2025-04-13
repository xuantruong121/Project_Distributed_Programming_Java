package iuh.fit.qlksfxapp.Enitty;

import iuh.fit.qlksfxapp.DAO.EntityManagerUtil;
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
    @JoinColumn(name = "maChiTietDonDatPhong")
    private ChiTietDonDatPhong chiTietDonDatPhong;

    @ManyToOne
    @JoinColumn(name = "maHoaDon")
    private HoaDon hoaDon;

    @PrePersist
    public void prePersist(){
        if(this.maChiTietDichVu == null){
            this.maChiTietDichVu = generateMaChiTietDichVu();
        }
    }
    public String generateMaChiTietDichVu() {
        String pattern = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyy"))
                + "-" + this.dichVu.getMaDichVu().substring(2, 4) + "-";
        String queryStr = "SELECT COUNT(c) FROM ChiTietDichVu c WHERE c.maChiTietDichVu LIKE :pattern";
        EntityManager em = null;
        long count = 0;
        try {
            em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
            Query query = em.createQuery(queryStr);
            query.setParameter("pattern", pattern + "%");
            count = (long) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
        return pattern + String.format("%06d", count + 1);
    }

}
