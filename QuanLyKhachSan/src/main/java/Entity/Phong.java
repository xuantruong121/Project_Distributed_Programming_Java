package Entity;

import DAO.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
//import java.util.regex.Pattern;
//import java.util.regex.Matcher;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Phong {
    @Id
    @Column(columnDefinition = "nvarchar(7)")
    @Pattern(regexp = "^(STA|SUP|DEL)-\\d{1}\\d{2}$",message = "ma phong không hợp lệ (PPP-YXX)")
    private String maPhong;
    @Column(columnDefinition = "nvarchar(30)")
    @NotBlank(message = "vi tri không được để trống")
    private String viTri;
    private String hinhAnh;
    private String moTa;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "trang thai phong không được để trống")
   private TrangThaiPhong trangThaiPhong;
    @ManyToOne
    @JoinColumn(name="maLoaiPhong")
    @NotNull(message = "loai phong không được để trống")
    private LoaiPhong loaiPhong;
    @PrePersist
    public void prePersist(){
        if(this.maPhong==null)
        {
            this.maPhong = generateMaPhong();
        }

    }
    public String generateMaPhong(){
        String tenLoaiPhong = "DEL";
        if(this.loaiPhong.getTenLoaiPhong().equals("Standard")){
           tenLoaiPhong="STA";
        }
        else if(this.loaiPhong.getTenLoaiPhong().equals("Superior")){
            tenLoaiPhong="SUP";
        }
        StringBuilder soTang = new StringBuilder();
        // Duyệt qua từng ký tự trong chuỗi
        for (int i = 0; i <this.viTri.length(); i++) {
            char c = this.viTri.charAt(i);
            // Kiểm tra nếu ký tự là số
            if (Character.isDigit(c)) {
                soTang.append(c);
            }
        }
        String maPhong = tenLoaiPhong+"-"+soTang;
        String query = "SELECT COUNT(p) FROM Phong p WHERE p.maPhong LIKE '" + maPhong + "%'";
        long count = (long) EntityManagerUtil.getEntityManagerFactory().createEntityManager().createQuery(query).getSingleResult();
        return maPhong + String.format("%02d",count + 1);
    }
//    @OneToMany(mappedBy = "phong")
//    private Set<ChiTietPhong> chiTietPhong;
//    public String generateMaPhong(){
//        return "P"+(int)(Math.random()*10000);
//    }
}
