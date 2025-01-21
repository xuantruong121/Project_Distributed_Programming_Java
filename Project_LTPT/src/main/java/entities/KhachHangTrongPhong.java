package entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "KhachHangTrongPhong")
@Getter
@Setter
@ToString
public class KhachHangTrongPhong {

    @EmbeddedId
    private KhachHangTrongPhongId id;

    @ManyToOne
    @JoinColumn(columnDefinition = "VARCHAR(50)", nullable = false)
    private ChiTietDonDatPhong chiTietDonDatPhong;

    @ManyToOne
    @JoinColumn(columnDefinition = "VARCHAR(40)", nullable = false)
    private KhachHang khachHang;
}
