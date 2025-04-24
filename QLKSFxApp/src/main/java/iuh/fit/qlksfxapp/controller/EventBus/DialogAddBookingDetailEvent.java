package iuh.fit.qlksfxapp.controller.EventBus;

import com.google.common.base.Supplier;

import java.util.List;

public class DialogAddBookingDetailEvent {
    private final List<String> selectedRooms;
    public DialogAddBookingDetailEvent(List<String> selectedRooms) {
        this.selectedRooms = selectedRooms;
    }
    public List<String> getSelectedRooms() {
        return selectedRooms;
    }
}
