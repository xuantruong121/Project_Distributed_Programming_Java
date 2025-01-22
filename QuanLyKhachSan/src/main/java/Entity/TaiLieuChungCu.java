package Entity;

import util.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class TaiLieuChungCu {
    @Id
    @Column(columnDefinition = "nvarchar(18)")
    @Pattern(regexp = "^\\d{4}-\\d{3}-\\d{3}-\\d{5}$",message = "Mã tài liệu chứng cứ không hợp lệ (MMYY-XXX-ZZZ-KKKKK)")
    private String maTaiLieu;

    @ManyToOne
    @JoinColumn(name = "maChiTietDonBaoCao")
    @NotNull
    private ChiTietDonBaoCao chiTietDonBaoCao;

    private String hinhAnh;
    private String video;
    private String taiLieu;

    @PrePersist
    public void prePersist(){
        if(this.maTaiLieu == null){
            this.maTaiLieu = generateMaTaiLieu();
        }
    }
    public String generateMaTaiLieu(){
        String maTaiLieu = this.chiTietDonBaoCao.getMaChiTiet() + "-";
        String query = "SELECT COUNT(t) FROM TaiLieuChungCu t WHERE t.maTaiLieu LIKE '" + maTaiLieu + "%'";
        long count = (long) EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return maTaiLieu + String.format("%05d",count + 1);
    }
}
