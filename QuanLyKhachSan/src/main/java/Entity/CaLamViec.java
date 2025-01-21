package Entity;

import DAO.EntityManagerUtil;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
@Entity
@Getter
@Setter
public class CaLamViec {
    @Id
    @Column(columnDefinition = "nvarchar(4)")
    @Pattern(regexp = "^CL\\d{2}$",message = "Mã ca làm không hợp lệ (CLXX)")
    private String maCaLam;
    @Column(columnDefinition = "nvarchar(50)")
    @NotBlank(message = "Tên ca làm không được để trống")
    private String tenCaLam;
    @NotNull(message = "Thời gian bắt đầu không được để trống")
    private LocalTime thoiGianBatDau;
    @NotNull(message = "Thời gian kết thúc không được để trống")
    private LocalTime thoiGianKetThuc;
    private String moTa;
    @PrePersist
    public void prePersist(){
        if(this.maCaLam == null){
            this.maCaLam = generateMaCaLam();
        }
    }
    public String generateMaCaLam(){
        String getNumbersOfMaCaLam = "SELECT COUNT(c) FROM CaLamViec c";
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        long numbersOfMaCaLam = (long)em.createQuery(getNumbersOfMaCaLam).getSingleResult();
        em.close();
        return "CL" + String.format("%02d",numbersOfMaCaLam + 1);
    }
}
