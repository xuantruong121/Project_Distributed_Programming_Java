package iuh.fit.qlksfxapp.Entity;

import iuh.fit.qlksfxapp.DAO.EntityManagerUtil;
import iuh.fit.qlksfxapp.Entity.Enum.LoaiDieuKien;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class DieuKienApDung {
    @Id
    @Column(columnDefinition = "nvarchar(10)")
    @Pattern(regexp = "^CT\\d{2}-\\d{3}-\\d{1}$",message = "Mã điều kiện không hợp lệ (CTYY-XXX-Z)")
    private String maDieuKien;
    @Enumerated(EnumType.STRING)
    @NotNull
    private LoaiDieuKien loaiDieuKien;
    @PositiveOrZero
    private int giaTriDieuKien;
    @ManyToOne
    @JoinColumn(name = "maChuongTrinhKhuyenMai")
    @NotNull
    private ChuongTrinhKhuyenMai chuongTrinhKhuyenMai;
    @PrePersist
    public void prePersist(){
        if(this.maDieuKien == null){
            this.maDieuKien = generateMaDieuKien();
        }
    }
    public String generateMaDieuKien(){
        String maDieuKien = this.chuongTrinhKhuyenMai.getMaChuongTrinhKhuyenMai()+ "-";
        String query = "SELECT COUNT(d) FROM DieuKienApDung d where d.maDieuKien like '" + maDieuKien + "%'";
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        long count = (long)em.createQuery(query).getSingleResult();
        em.close();
        return maDieuKien + String.format("%01d",count + 1);
    }
}
