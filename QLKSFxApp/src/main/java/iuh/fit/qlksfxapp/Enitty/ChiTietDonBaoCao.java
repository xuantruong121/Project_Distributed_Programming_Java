package iuh.fit.qlksfxapp.Enitty;

import iuh.fit.qlksfxapp.DAO.EntityManagerUtil;
import iuh.fit.qlksfxapp.Enitty.Enum.MucDoThietHai;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class ChiTietDonBaoCao {
    @Id
    @Column(columnDefinition = "nvarchar(12)")
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{3}$",message = "Mã chi tiết đơn báo cáo không hợp lệ (MMYY-XXX-ZZZ)")
    private String maChiTiet;
    @ManyToOne
    @JoinColumn(name = "maDonBaoCao")
    @NotNull
    private DonBaoCao donBaoCao;
    @ManyToOne
    @JoinColumn(name = "maVatTu")
    @NotNull
    private VatTu vatTu;
    @PositiveOrZero
    private double soLuong;
    @Enumerated(EnumType.STRING)
    @NotNull
    private MucDoThietHai mucDoThietHai;
    @OneToMany(mappedBy = "chiTietDonBaoCao",fetch = FetchType.LAZY)
    private List<TaiLieuChungCu> taiLieuChungCu;
    @PrePersist
    public void prePersist(){
        if(this.maChiTiet == null){
            this.maChiTiet = generateMaChiTiet();
        }
    }
    public String generateMaChiTiet(){
        String maChiTiet = this.donBaoCao.getMaDonBaoCao() + "-";
        String query = "SELECT COUNT(c) FROM ChiTietDonBaoCao c WHERE c.maChiTiet LIKE '" + maChiTiet + "%'";
        long count = (long) EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return maChiTiet + String.format("%03d",count + 1);
    }
}
