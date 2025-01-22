package Entity;

import util.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import Enum.TrangThaiVatTu;

@Entity
@Getter
@Setter
public class ChiTietPhong {
    @Id
    @Column(columnDefinition = "nvarchar(7)")
    @Pattern(regexp = "^\\d{3}-\\d{3}$",message = "Mã chi tiết phòng không hợp lệ (YXX-ZZZ)")
    private String maChiTietPhong;
    @ManyToOne
    @JoinColumn(name = "maPhong")
    @NotNull
    private Phong phong;
    @ManyToOne
    @JoinColumn(name = "maVatTu")
    @NotNull
    private VatTu vatTu;
    @PositiveOrZero
    private double soLuong;
    @Enumerated(EnumType.STRING)
    @NotNull
    private TrangThaiVatTu trangThaiVatTu;
    @PrePersist
    public void prePersist(){

        if(this.maChiTietPhong == null){
            this.maChiTietPhong = generateMaChiTietPhong();
        }
    }
    public String generateMaChiTietPhong(){
        String maPhong = this.phong.getMaPhong().substring(4)+"-";
        String query = "SELECT COUNT(c) FROM ChiTietPhong c where c.maChiTietPhong like '" + maPhong + "%'";
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        long count = (long)em.createQuery(query).getSingleResult();
        em.close();
        return maPhong+ String.format("%03d",count + 1);
    }
}
