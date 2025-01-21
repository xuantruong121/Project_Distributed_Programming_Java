package Enum;

public enum TrangThaiBangPhanCongCaLam {
    DA_lEN_lICH("Đã lên lịch"), HOAN_THANH ("Hoàn thành"), CHUA_HOAN_THANH("Chưa hoàn thành");
    private final String s;
    TrangThaiBangPhanCongCaLam(String s) {
        this.s = s;
    }
    @Override
    public String toString() {
        return s;
    }
}
