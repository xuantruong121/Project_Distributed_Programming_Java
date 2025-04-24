package iuh.fit.qlksfxapp.Entity;

import iuh.fit.qlksfxapp.DAO.Impl.EntityManagerUtilImpl;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonBaoCao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class DonBaoCao implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(columnDefinition = "nvarchar(8)")
    @Pattern(regexp = "^\\d{4}-\\d{3}$",message = "Mã đơn báo cáo không hợp lệ (MMYY-XXX)")
    private String maDonBaoCao;
    @ManyToOne
    @JoinColumn(name = "maNhanVien")
    @NotNull
    private NhanVien nhanVien;
    @ManyToOne
    @JoinColumn(name = "maChiTietDonDatPhong")
    @NotNull
    private ChiTietDonDatPhong chiTietDonDatPhong;
    @NotNull
    @PastOrPresent
    private LocalDateTime ngayLap;
    private String moTa;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TrangThaiDonBaoCao trangThaiDonBaoCao;
    @PositiveOrZero
    private double tongChiPhiUocTinh;
    @PrePersist
    public void prePersist(){
        if(this.maDonBaoCao == null){
            this.maDonBaoCao = generateMaDonBaoCao();
        }
    }
    public String generateMaDonBaoCao(){
        LocalDateTime now = LocalDateTime.now();
        String maDonBaoCao = String.format("%02d",now.getMonthValue()) + String.format("%02d",now.getYear()%100) + "-";
        String query = "SELECT COUNT(d) FROM DonBaoCao d WHERE d.maDonBaoCao LIKE '" + maDonBaoCao + "%'";
        long count = (long) EntityManagerUtilImpl.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return maDonBaoCao + String.format("%03d",count + 1);
    }
}


