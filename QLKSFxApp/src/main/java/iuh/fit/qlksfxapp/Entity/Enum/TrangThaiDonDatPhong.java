package iuh.fit.qlksfxapp.Entity.Enum;

public enum TrangThaiDonDatPhong {

    DA_XAC_NHAN ("Đã xác nhận"),
    KHONG_DEN("Không đến"),
    TAM_HOAN("Tạm hoãn"),
    DA_HUY_VA_HOAN_TIEN("Đã hủy và hoàn tiền"),
    DA_HUY("Đã hủy");
    public String s;

    TrangThaiDonDatPhong(String s){
        this.s = s;
    }
}
