package iuh.fit.qlksfxapp.Entity.Enum;

public enum LoaiKhuyenMai {
    THEO_PHAN_TRAM("Theo phần trăm"),
    THEO_TIEN_CO_DINH("Theo tiền cố định"),
    TANG_DICH_VU("Tặng dịch vụ");
    public String s;
    LoaiKhuyenMai(String s) {
        this.s = s;
    }
}
