package enums;

public enum TrangThaiChiTietDonDatPhong {
    DAT_TRUOC,
    DA_NHAN_PHONG,
    DA_TRA_PHONG,
    DA_HUY,
    DA_THANH_TOAN;

    public String getTrangThaiChiTietDonDatPhong() {
        switch (this) {
            case DAT_TRUOC:
                return "Dat truoc";
            case DA_NHAN_PHONG:
                return "Da nhan phong";
            case DA_TRA_PHONG:
                return "Da tra phong";
            case DA_HUY:
                return "Da huy";
            case DA_THANH_TOAN:
                return "Da thanh toan";
            default:
                return null;
        }
    }
}
