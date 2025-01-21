package enums;

public enum TrangThaiPhong {
    TRONG,
    DANG_SU_DUNG,
    DANG_DON_DEP,
    DAT_TRUOC,
    DANG_SUA_CHUA,
    KHONG_SU_DUNG;

    public static TrangThaiPhong getTrangThaiPhong(String trangThai) {
        switch (trangThai) {
            case "TRONG":
                return TRONG;
            case "DANG_SU_DUNG":
                return DANG_SU_DUNG;
            case "DANG_DON_DEP":
                return DANG_DON_DEP;
            case "DAT_TRUOC":
                return DAT_TRUOC;
            case "DANG_SUA_CHUA":
                return DANG_SUA_CHUA;
            case "KHONG_SU_DUNG":
                return KHONG_SU_DUNG;
            default:
                return null;
        }
    }
}
