package iuh.fit.qlksfxapp.controller.EventBus;

import iuh.fit.qlksfxapp.Entity.KhachHang;

public class DialogAddKhachHangEvent {
    public enum Context {
        BOOKING_FORM,
        DETAIL_BOOKING_FORM
    }
    private KhachHang khachHang;
    private  Context context;
    public DialogAddKhachHangEvent(KhachHang khachHang,Context context) {
        this.khachHang = khachHang;
        this.context = context;
    }
    public KhachHang getKhachHang() {
        return khachHang;
    }
    public Context getContext() {
        return context;
    }
}
