package Entity.Enum;

public enum TrangThaiDonBaoCao {
    DANG_XU_LY("Đang xử lý"),
    DA_XU_LY("Đã xử lý"),
    DA_HUY("Đã hủy");
    private String s;
    TrangThaiDonBaoCao (String s) {
        this.s=s;
    }
}
