package iuh.fit.qlksfxapp.controller.EventBus;

import iuh.fit.qlksfxapp.Entity.KhachHang;

public class DialogAddKhachHangEvent {
    private KhachHang khachHang;
    public DialogAddKhachHangEvent(KhachHang kh) {
        this.khachHang = kh;
    }
    public KhachHang getKhachHang() {
        return khachHang;
    }
}
