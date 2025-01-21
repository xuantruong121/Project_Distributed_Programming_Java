package enums;

public enum TrangThaiNhanVien {
    DANG_LAM_VIEC,
    DA_NGHI_VIEC,
    TAM_NGHI;

    public static TrangThaiNhanVien fromString(String text) {
        for (TrangThaiNhanVien b : TrangThaiNhanVien.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
