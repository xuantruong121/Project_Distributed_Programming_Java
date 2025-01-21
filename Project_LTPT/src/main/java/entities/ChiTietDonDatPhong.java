package entities;

import enums.TrangThaiChiTietDonDatPhong;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Entity
@Table(name = "ChiTietDonDatPhong")
@Getter
@Setter
@ToString
public class ChiTietDonDatPhong {

    @Id
    @Column(columnDefinition = "varchar(50)", nullable = false)
    private String maChiTietDonDatPhong;

    @ManyToOne
    @JoinColumn(name = "maDonDatPhong", nullable = false)
    private DonDatPhong donDatPhong;

    @ManyToOne
    @JoinColumn(name = "maPhong", nullable = false)
    private Phong phong;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime ngayNhan;

    @Column(columnDefinition = "DATETIME", nullable = false)
    private LocalDateTime ngayTra;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private TrangThaiChiTietDonDatPhong trangthai;
}
