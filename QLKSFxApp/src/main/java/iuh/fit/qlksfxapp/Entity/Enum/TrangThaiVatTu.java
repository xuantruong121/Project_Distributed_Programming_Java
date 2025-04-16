package iuh.fit.qlksfxapp.Entity.Enum;

public enum TrangThaiVatTu {
    THAT_LAC("Thất lạc"),
    DANG_SU_DUNG("Đang sử dụng"),
    HONG_HOC("Hỏng hóc"),
    DANG_SUA_CHUA("Đang sửa chữa");
    private String s;
    TrangThaiVatTu(String s) {
        this.s = s;
    }
}
