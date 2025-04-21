package iuh.fit.qlksfxapp.Entity;

import iuh.fit.qlksfxapp.DAO.Impl.EntityManagerUtilImpl;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiBangPhanCongCaLam;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Entity
@Setter
@Getter
public class BangPhanCongCaLam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "nvarchar(12)")
    @Pattern(regexp = "^PC\\d{6}-\\d{3}$",message = "Mã phân công không hợp lệ (PCDDMMYY-XXX)")
    private String maPhanCong;
    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    @NotNull
    private NhanVien nhanVien;
    @ManyToOne
    @JoinColumn(name = "maCaLam",nullable = false)
    @NotNull
    private CaLamViec caLamViec;
    @NotNull
    private LocalDate ngayLamViec;
    @Enumerated(EnumType.STRING)
    private TrangThaiBangPhanCongCaLam trangThai;
    @PrePersist
    public void prePersist(){
        if(this.maPhanCong == null){
            this.maPhanCong = generateMaPhanCong();
        }
    }
    public String generateMaPhanCong(){
        String pattern = "PC" + LocalDate.now().format(DateTimeFormatter.ofPattern("ddMMyy"));
        String getNumbersOfMaPhanCong = "SELECT COUNT(b) FROM BangPhanCongCaLam b where b.maPhanCong like '" + pattern + "%'";
        EntityManager em = EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager();
        long numbersOfMaPhanCong = (long)em.createQuery(getNumbersOfMaPhanCong).getSingleResult();
        em.close();
        return pattern + String.format("%03d",numbersOfMaPhanCong + 1);
    }

}

