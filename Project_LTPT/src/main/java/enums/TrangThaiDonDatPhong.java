package enums;

public enum TrangThaiDonDatPhong {
    DA_XAC_NHAN,
    KHONG_DEN,
    TAM_HOAN,
    DA_HUY_VA_HOAN_LAI_TIEN,
    DA_HUY;

    public String getTrangThaiDonDatPhong() {
        switch (this) {
            case DA_XAC_NHAN:
                return "Da xac nhan";
            case KHONG_DEN:
                return "Khong den";
            case TAM_HOAN:
                return "Tam hoan";
            case DA_HUY_VA_HOAN_LAI_TIEN:
                return "Da huy va hoan lai tien";
            case DA_HUY:
                return "Da huy";
            default:
                return null;
        }
    }
}
