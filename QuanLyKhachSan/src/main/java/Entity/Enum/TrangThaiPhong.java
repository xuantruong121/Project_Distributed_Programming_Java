package Entity.Enum;

public enum TrangThaiPhong {
    TRONG("Trống"),
    DANG_SU_DUNG("Đang sử dụng"),
    DANG_DON_DEP("Đang dọn dẹp"),
    DAT_TRUOC("Đặt trước"),
    DANG_SUA_CHUA("Đang sửa chữa"),
    KHONG_SU_DUNG("Không sử dụng");
    public String s;
    TrangThaiPhong(String s) {
        this.s = s;
    }

}
