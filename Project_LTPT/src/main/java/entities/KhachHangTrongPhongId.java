package entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Embeddable
@Getter
@Setter
@ToString
public class KhachHangTrongPhongId implements java.io.Serializable{

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    private String maChiTietDonDatPhong;

    @Column(columnDefinition = "VARCHAR(40)", nullable = false)
    private String maKhachHang;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        KhachHangTrongPhongId that = (KhachHangTrongPhongId) o;
        return Objects.equals(maChiTietDonDatPhong, that.maChiTietDonDatPhong) && Objects.equals(maKhachHang, that.maKhachHang);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maChiTietDonDatPhong, maKhachHang);
    }
}
