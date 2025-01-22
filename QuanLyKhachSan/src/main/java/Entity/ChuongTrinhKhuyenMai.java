package Entity;

import util.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;
import Enum.LoaiKhuyenMai;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class ChuongTrinhKhuyenMai {
    @Id
    @Column(columnDefinition = "nvarchar(8)")
    @Pattern(regexp = "^CT\\d{2}-\\d{3}$",message = "Mã chương trình khuyến mãi không hợp lệ (CTYY-XXX)")
    private String maChuongTrinhKhuyenMai;
    @NotBlank(message = "Tên chương trình khuyến mãi không được để trống")
    private String tenChuongTrinhKhuyenMai;
    private String moTa;
    @Enumerated(EnumType.STRING)
    @NotNull
    private LoaiKhuyenMai loaiKhuyenMai;
    @PositiveOrZero
    private double giaTriKhuyenMai;
    @NotNull
    private LocalDateTime ngayApDung;
    @NotNull
    private LocalDateTime ngayKetThuc;
    @NotNull
    private boolean trangThai;
    private String hinhAnh;
//    @OneToMany(mappedBy = "chuongTrinhKhuyenMai")
//    private Set<DieuKienApDung> dieuKienApDung;
    @PrePersist
    public void prePersist(){
        if(this.maChuongTrinhKhuyenMai == null){
            this.maChuongTrinhKhuyenMai = generateMaChuongTrinhKhuyenMai();
        }
    }
    public String generateMaChuongTrinhKhuyenMai(){
        int year = LocalDateTime.now().getYear() % 100;
        String maChuongTrinhKhuyenMai = "CT" + String.format("%02d",year) + "-";
        String query = "SELECT COUNT(c) FROM ChuongTrinhKhuyenMai c where c.maChuongTrinhKhuyenMai like '" + maChuongTrinhKhuyenMai + "%'";
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        long count = (long)em.createQuery(query).getSingleResult();
        em.close();
        return maChuongTrinhKhuyenMai + String.format("%03d",count + 1);
    }
}
