package enums;

public enum TrangThaiBangPhanCongCaLam {
    DA_LEN_LICH,
    HOAN_THANH,
    CHUA_HOAN_THANH;

    public static TrangThaiBangPhanCongCaLam fromString(String text) {
        for (TrangThaiBangPhanCongCaLam b : TrangThaiBangPhanCongCaLam.values()) {
            if (b.name().equalsIgnoreCase(text)) {
                return b;
            }
        }
        return null;
    }
}
