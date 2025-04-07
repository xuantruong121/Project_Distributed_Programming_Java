package Entity;

import DAO.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DoiTuongApDungKhuyenMai {
    @Id
    @Column(columnDefinition = "nvarchar(15)")
    @Pattern(regexp = "^CT\\d{2}-\\d{3}-DT\\d{4}$",message = "Mã đối tượng không hợp lệ (CTYY-XXX-DTZZZZ)")
    private String maDoiTuongApDung;
    @ManyToOne
    @JoinColumn(name = "maChuongTrinhKhuyenMai")
    private ChuongTrinhKhuyenMai chuongTrinhKhuyenMai;
    @ManyToOne
    @JoinColumn(name = "maKhachHang")
    private KhachHang khachHang;
    @ManyToOne
    @JoinColumn(name = "maPhong")
    private Phong phong;
    @ManyToOne
    @JoinColumn(name = "maDonDatPhong")
    private DonDatPhong donDatPhong;
    @ManyToOne
    @JoinColumn(name = "maDichVu")
    private DichVu dichVu;
    @Column(columnDefinition = "boolean default false")
    private boolean loaiBoKhachHang;
    @Column(columnDefinition = "boolean default false")
    private boolean loaiBoPhong;
    @Column(columnDefinition = "boolean default false")
    private boolean loaiBoDonDatPhong;
    @Column(columnDefinition = "boolean default false")
    private boolean loaiBoDichVu;
    
    @PrePersist
    public void prePersist(){
        if(this.maDoiTuongApDung == null){
            this.maDoiTuongApDung = generateMaDoiTuongApDung();
        }
    }
    public String generateMaDoiTuongApDung(){
        String pattern = this.chuongTrinhKhuyenMai.getMaChuongTrinhKhuyenMai()+"-DT";
        String query = "SELECT COUNT(d) FROM DoiTuongApDungKhuyenMai d where d.maDoiTuongApDung like '" + pattern + "%'";
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        long count = (long)em.createQuery(query).getSingleResult();
        em.close();
        return pattern+ String.format("%04d",count + 1);
    }
}
