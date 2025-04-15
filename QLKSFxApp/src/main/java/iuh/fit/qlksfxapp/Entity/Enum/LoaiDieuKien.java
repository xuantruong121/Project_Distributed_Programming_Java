package iuh.fit.qlksfxapp.Entity.Enum;

public enum LoaiDieuKien {
    PHONG("Phòng"),
    SO_DEM("Số đêm"),
    KHACH_HANG("Khách hàng"),
    DIEM_TICH_LUY("Điểm tích lũy");
    private final String s;
    LoaiDieuKien(String s) {
        this.s = s;
    }
    @Override
    public String toString() {
        return s;
    }
}
