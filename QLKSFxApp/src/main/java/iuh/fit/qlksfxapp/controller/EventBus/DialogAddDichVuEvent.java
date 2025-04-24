package iuh.fit.qlksfxapp.controller.EventBus;

import iuh.fit.qlksfxapp.Entity.DichVu;

import java.util.List;

public class DialogAddDichVuEvent {
    private DichVu selectedDichVu;
    private Integer soLuong;
    public DialogAddDichVuEvent(DichVu selectedDichVu, Integer soLuong) {
        this.selectedDichVu = selectedDichVu;
        this.soLuong = soLuong;
    }
    public DichVu getSelectedDichVu() {
        return selectedDichVu;
    }
    public Integer getSoLuong() {
        return soLuong;
    }
}
