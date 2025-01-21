package Entity;

public enum TrangThaiChiTietDonDatPhong {
    DAT_TRUOC("Đặt trước"),
    DA_NHAN_PHONG("Đã nhận phòng"),
    DA_TRA_PHONG("Đã trả phòng"),
    DA_HUY("Đã hủy"),
    DA_THANH_TOAN("Đã thanh toán");
    private String s;
    TrangThaiChiTietDonDatPhong (String s){
        this.s=s;
    }
}
