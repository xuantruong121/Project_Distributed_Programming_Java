package Entity;

import util.EntityManagerUtil;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class LoaiPhong {
    @Id
    @Column(columnDefinition = "nvarchar(3)")
    @Pattern(regexp = "^LP\\d{1}$",message = "ma loai phong không hợp lệ (LPX)")
    private String maLoaiPhong;
    @NotBlank(message = "ten loai phong không được để trống")
    @Column(columnDefinition = "nvarchar(20)")
    private String tenLoaiPhong;
    @PositiveOrZero(message = "so luong nguoi lon không được âm")
    private int soLuongNguoiLon;
    @PositiveOrZero(message = "so luong tre em không được âm")
    private int soLuongTreEm;
    @PositiveOrZero(message = "so luong giuong doi không được âm")
    private double dienTich;
    @Positive(message = "so luong giuong doi không được âm")
    private double gia;
    private String moTa;

    @PrePersist
    public void prePersist(){
        if(this.maLoaiPhong == null){
            this.maLoaiPhong = generateMaLoaiPhong();
        }
    }
    public String generateMaLoaiPhong(){
        String query = "SELECT COUNT(l) FROM LoaiPhong l";
        long count = (long) EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return "LP" + (count+1);
    }
}
